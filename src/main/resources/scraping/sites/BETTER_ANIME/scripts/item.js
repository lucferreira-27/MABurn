(query) => {


    //GET ALL PLAYES

    //var playersQuerrys = ["quality480p", "quality720","quality1080p"];

      var btn  = document.querySelector("#" + "quality1080p");
        console.log("#" + "quality1080p")
        if(btn != null){
           btn.click();
           return getVideoSrc();
        }
        

    function getVideoSrc(){
        var player = document.querySelector("#playerFrame")
        var content =  player.contentDocument.querySelector("body").outerHTML
        var index = content.indexOf('{"file":')
        var cutContent = content.substring('{"file":'.length + index);
        var src = (cutContent.substring(0, cutContent.indexOf("},")));
        src = src.replace("480p", "1080p").replaceAll("\\/", "/").replace('"', "");
       
        if (src.includes("video.wixstatic.com"))
         src = "https://" + src.substring(src.indexOf("video.wixstatic.com"),
                "file.mp4".length + src.lastIndexOf("file.mp4"));
        return src


    }


}
