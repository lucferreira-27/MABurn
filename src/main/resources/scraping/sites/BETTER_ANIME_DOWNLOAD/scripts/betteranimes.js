

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

    maburn.waitElement(".mt-4 a").then(async () => {
        var buttons = Array.from(document.querySelectorAll(".mt-4 a"))
        maburn.send("CLICK", `.mt-4 a:nth-child(${buttons.length + 1})`)
    })

    maburn.waitElement("h3.pt-4.mb-0.pb-5").then(async () => {
        values[0].url = await getVideoSrc()
        maburn.send("RESULT", JSON.stringify(values))

    })

    async function getVideoSrc() {

        return await maburn.waitElement("button.mb-5.btn.btn-warning").then(() => {
            var btnGenerateLink = document.querySelector('.mb-5.btn.btn-warning');
            btnGenerateLink.click();
            return maburn.waitElement(".mb-5.btn.btn-primary");

        }).then(() => {
            var btnDownload = document.querySelector('.mb-5.btn.btn-primary');
            return btnDownload.href;
        })




    }


}

function getAllNames() {

    maburn.waitElement("#episodesList .list-group-item.list-group-item-action div a:nth-child(3)").then(async () => {
        let episodios = document.querySelectorAll("#episodesList .list-group-item.list-group-item-action div a:nth-child(3)")
        let txtEpisodios = []
        episodios.forEach(e => {
            txtEpisodios.push({ name: e.title.replace(/.*-\s+/g, ' ').replace(/\s$/g,''), url: e.href })
        })
        console.log(txtEpisodios)

        maburn.send("RESULT", JSON.stringify(txtEpisodios))


    })

}