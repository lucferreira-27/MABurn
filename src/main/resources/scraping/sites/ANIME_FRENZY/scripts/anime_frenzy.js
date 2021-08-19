



let episodes = []
function execute(target) {
    if (maburn.active) {
        console.log("Maburn already actived!")
        return
    }
    if (!target){
           maburn.screenshot()
    }   
    if(!target)
        maburn.waitElement('.infovan .infoept1').then(async ()=>{

            document.querySelectorAll('.infoepbox .infovan').forEach((e) =>{
                var name =  e.querySelector('.infoept1').innerText
                var number =  e.querySelector('.infoept2').innerText
                var url = e.href             
                episodes.push({name : name  + " " + number, url :url})
            })
            maburn.send("RESULT",JSON.stringify(episodes))
            maburn.exit()
        })
    else
        maburn.waitElement('.centerf2').then(async ()=>{
            var listDownloadLink = document.querySelectorAll('.centerf2 .an')[0].href
            maburn.send('GOTO',listDownloadLink)

     

        })
        maburn.waitElement('.mirror_link').then(async ()=>{
            var listDownloadsOptions = document.querySelectorAll('div.mirror_link')[0].querySelectorAll('div');
            var bestOption = listDownloadsOptions[listDownloadsOptions.length - 1]
            var link = bestOption.querySelector('a').href
            episodes.push({name : target, url : link})
            maburn.send('RESULT',JSON.stringify(episodes))
            maburn.exit()
        })






}



