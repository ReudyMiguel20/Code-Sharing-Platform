<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<div>
    <#list latestcodes as codeexamples>
    <span id="load_date" style="display: block">${codeexamples.date}</span>
    <pre id="code_snippet" style="border: solid black 2px; padding: 10px; display: inline-block;"><code>${codeexamples.code}</code></pre>
</div>
</#list>
</body>
</html>