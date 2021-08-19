



let values = []
function execute(target) {
    if (maburn.active) {
        console.log("Maburn already actived!")
        return
    }

    if(!target){
        maburn.screenshot()
        maburn.waitElement('.listing  a[title][class]').then(async ()=>{

            document.querySelectorAll('.listing  a[title][class]').forEach((ep) =>{
                values.push({name : ep.innerText, url : ep.href})
            })
            maburn.send("RESULT",JSON.stringify(values))
            maburn.exit()
        })
    }else{
        maburn.waitElement("#player_container").then(async ()=>{
            var listDownloadLink = document.querySelector("#player_container").querySelector("iframe").src
            maburn.send('GOTO',listDownloadLink)
        })

        maburn.waitElement('video').then(async ()=>{
            var link =  document.querySelector('video').src
            values.push({name : target, url : link})
            maburn.send('RESULT',JSON.stringify(values))
            maburn.exit()
        })
    }





}



