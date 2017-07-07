package vision;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import japp.util.StringHelper;
import japp.web.WebApp;
import japp.web.controller.http.HttpController;
import japp.web.controller.http.annotation.RequestParameter;
import japp.web.controller.http.annotation.Requestable;
import japp.web.dispatcher.http.request.RequestMethod;
import japp.web.view.View;
import vision.core.image.ImageRepresentation;
import vision.core.service.StockVisionService;

@Requestable("/")
public class AppController extends HttpController {
	
	@Requestable(value = "/ImageUpload")
	public View imageUpload() {
		return new View("full", "image_upload");
	}
	
	@Requestable(value = "/ImageUpload", method = RequestMethod.POST)
	public View upload(@RequestParameter("file") Part imagePart, HttpServletRequest httpServletRequest) throws IOException {
		ImageRepresentation imageRepresentation = WebApp.getService(StockVisionService.class).get().process(imagePart.getInputStream());
		
		httpServletRequest.setAttribute("image", StringHelper.base64Encode(imageRepresentation.convertToBytes()));
		httpServletRequest.setAttribute("imageContentType", imageRepresentation.getContentType());
		httpServletRequest.setAttribute("imageWidth", imageRepresentation.getWidth());
		httpServletRequest.setAttribute("imageHeight", imageRepresentation.getHeight());
		
		return new View("full", "image_upload");
	}
	
	// http://localhost:8080/OCR/test?food1=banana&date=2016-08-11T02:26:16.837&array=item1&array=item2&dates=2016-08-12T02:26:16.839&dates=2016-08-13T02:26:16.838
	@Requestable(value = "/test")
	public String test(@RequestParameter("food1") String food, @RequestParameter Date date, @RequestParameter(required = false) boolean isTrue, @RequestParameter String[] array, @RequestParameter(required = false) Date[] dates) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("i'm just testing " + food + " at: " + date + ", " + isTrue);
		
		if (array != null) {
			for (String item : array) {
				stringBuilder.append("\r\n" + item);
			}
		}
		
		if (dates != null) {
			for (Date item : dates) {
				stringBuilder.append("\r\n" + item);
			}
		}
		
		return stringBuilder.toString();
	}
}
