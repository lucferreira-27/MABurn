

let values = []


function execute(target) {
    if (maburn.active) {
        console.log("Maburn already actived!")
        return
    }

    maburn.on
    if (!target) {
        maburn.screenshot()
        getAllNames()
    } else {
        values = [{ name: "", url: "" }]
        values[0].name = target
        getUrl(target)
    }




}


async function getUrl() {

    var buttons = Array.from(document.querySelectorAll("#qualitiesColumn button"))
    var selectBtn = buttons[buttons.length - 1];
    if (!selectBtn.className.includes("active")) {
        maburn.send("CLICK", `#qualitiesColumn button:nth-child(${buttons.length})`)
        await maburn.sleep(1000)
    }

    var src = getVideoSrc()


    function getVideoSrc() {
        maburn.waitElement("#playerFrame").then(() => {
            var player = document.querySelector("#playerFrame")
            var content = player.contentDocument.querySelector("body").outerHTML
            var index = content.indexOf('{"file":')
            var cutContent = content.substring('{"file":'.length + index);
            var src = (cutContent.substring(0, cutContent.indexOf(",") - 1));
            src = src.replace("480p", "1080p").replaceAll("\\/", "/").replace('"', "");

            if (src.includes("video.wixstatic.com"))
                src = "https://" + src.substring(src.indexOf("video.wixstatic.com"),
                    "file.mp4".length + src.lastIndexOf("file.mp4"));
            values[0].url = src;
            maburn.send("RESULT", JSON.stringify(values))
        })




    }

}

function getAllNames() {

    maburn.waitElement("#episodesList li a").then(async () => {
        let episodios = document.querySelectorAll("#episodesList li a")
        let txtEpisodios = []
        episodios.forEach(e => {
            if (e.classList.length == 0) {
                txtEpisodios.push({ name: e.text.replace(/\s+/g, ' ').replace(/\s$/g,''), url: e.href })
            }
        })
        console.log(txtEpisodios)

        maburn.send("RESULT", JSON.stringify(txtEpisodios))


    })

}