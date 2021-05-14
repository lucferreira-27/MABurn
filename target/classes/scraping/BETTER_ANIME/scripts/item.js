(query) => {
    var content =  document.body.outerHTML;
    var index = content.indexOf('[{"file":')
    var cutContent = content.substring('[{"file":'.length + index);
    var src = (cutContent.substring(0, cutContent.indexOf("}],")));
    src = src.replace("480p", "1080p").replaceAll("\\/", "/").replace('"', "");
   
    if (src.includes("video.wixstatic.com"))
     src = "http://" + src.substring(src.indexOf("video.wixstatic.com"),
            "file.mp4".length + src.lastIndexOf("file.mp4"));
    return src;
}
