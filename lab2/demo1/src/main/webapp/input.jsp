<%--
  Created by IntelliJ IDEA.
  User: Gabi
  Date: 15.10.2023
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload DIMACS Graph</title>
</head>
<body>
<h1>Upload DIMACS Graph</h1>
<form action="UploadServlet" method="post" enctype="multipart/form-data">
    <input type="file" name="graphFile" accept=".txt">
    <input type="submit" value="Upload">
</form>
</body>
</html>

