<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="format-detection" content="telephone=no">
        <meta name="description" content="${__appName__} ${__appVersion__}" />
        <meta name="keywords" content="${__appName__}" />
        <meta name="author" content="gerenciar.me" />
        <title>${__appName__}</title>
    </head>
    <body>
        <c:import url="${__view__}"></c:import>
    </body>
</html>