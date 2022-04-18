

let values = []


function execute(target) {
    if (maburn.active) {
        console.log("Maburn already actived!")
        return
    }

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


    maburn.waitElement("#inner_page").then(() => {
        let url = Array.from(document.querySelectorAll("#inner_page div"))
            .filter(div => div.querySelector(".finfo"))
            .map(div =>{
                let size = parseFloat(div.querySelector(".finfo > b").textContent.replaceAll(/\D/g,""))
                let url = div.querySelector("div > a").href
                return {size,url}
            })
            .sort((a,b) => b.size > a.size ? 1 : -1)[0].url
        values[0].url = url
        maburn.send("RESULT", JSON.stringify(values))
    })

}

function getAllNames() {

    maburn.waitElement(".episode").then(async () => {

        let episodios =  Array.from(document.querySelectorAll(".episode"))
            .map((episode) => {
            
            let epType = episode.querySelector("em").textContent.toUpperCase()
            let epNumber = episode.querySelector("strong").textContent
            let epName = episode.querySelector("i") ? episode.querySelector("i").textContent : ""

            let url = episode.querySelector(".download-link").href
            return {
                name: epType  + " " +  epNumber + epName,
                url: url
            }
        })


        maburn.send("RESULT", JSON.stringify(episodios))


    })

}