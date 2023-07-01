<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<span id="load_date">${code.date}</span>
<span id="views_restriction" style="display: block">${code.views} more views allowed</span>
<span id="time_restriction">The code will be available for ${code.secondsToExpire} seconds</span>
<br>
<pre id="code_snippet" style="border: solid black 2px; padding: 10px; display: inline-block;"><code>${code.code}</code></pre>
</body>
</html>