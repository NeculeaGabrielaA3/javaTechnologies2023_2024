<jsp:useBean id="graph" scope="request" type="com.example.demo1.model.Graph" />

<!DOCTYPE html>
<html>
<head>
    <title>Your JSP Page</title>
    <style>
        body {
            font-family: Verdana, sans-serif;
            background-color: #f5f5f5;
            text-align: center;
            margin: 0;
            padding: 0;
        }

        p {
            font-size: 18px;
            font-family: Calibri, serif;
            margin: 10px 0;
        }

        h1 {
            color: #333;
            margin-top: 20px;
        }

        .box {
            background-color: #f8d4d4;
            border: 2px #333;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(101, 25, 25, 0.2);
            width: 50%;
            margin: 0 auto;
            padding: 20px;
            text-align: center;
        }

        .box p {
            color: #333;
            margin: 10px 0;
        }
    </style>
</head>
<body>
<h1>Information about the graph:</h1>

<div class="box">
    <p>The graph's order is: ${graph.order}</p>
    <p>The graph's size is: ${graph.size}</p>
    <p>The graph's number of connected components is: ${graph.numberOfConnectedComponents}</p>
</div>
</body>
</html>
