(query) => {
    var content =  document.body.outerHTML;
    var index = content.indexOf('[{"file":')
    var cutContent = content.substring('[{"file":'.length + index);
    var src = (cutContent.substring(0, cutContent.indexOf(",")));
    src = src.replaceAll("\\/", "/").replaceAll('"', "");
   
    return src;
}
