() => {
    var content =  document.body.outerHTML;
    var index = content.indexOf('file:')
    var cutContent = content.substring('file:'.length + index + 2);
    var src = cutContent.substring(0, cutContent.indexOf('"'));
   

    return src;
}
