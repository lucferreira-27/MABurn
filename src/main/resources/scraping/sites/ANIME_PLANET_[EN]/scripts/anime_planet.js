



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
        maburn.waitElement('.videoWatchList li a').then(async ()=>{
            var cards = document.querySelectorAll(".videoWatchList li")
            
            cards.forEach(card =>{
                var anchor = card.querySelector('a');
                var parag = card.querySelector('p');
            
                var epName = anchor.innerText
                var url = anchor.href
                var epNameExt = parag.innerText
                episodes.push({name : epName + " " + epNameExt, url : url})
            
            })
            maburn.send("RESULT",JSON.stringify(episodes))
            maburn.exit()
        })
    else
        maburn.waitElement('#videoPlayerComponent iframe').then(async ()=>{
            var iframe =  document.querySelector("#videoPlayerComponent iframe");
            if(!iframe.src){
                await maburn.sleep(2000)
            }
            var iframeLink = iframe.src;
            maburn.send('GOTO',iframeLink)

        })
        maburn.waitElement('#vilos-player').then(async ()=>{
                


            var script = Array.from(document.querySelectorAll('script')).filter(e => e.textContent.includes('VilosPlayer') && !e.textContent.includes('Maburn'))[0]
            var content = script.textContent.substring()
            var videos = JSON.parse(getString(content , "vilos.config.media = ", ";"))
            var url = videos.streams.filter((stream) =>stream.hardsub_lang == 'enUS'  && stream.format == 'vo_adaptive_hls')[0].url

       
            episodes.push({name : target, url : url})
            maburn.send('RESULT',JSON.stringify(episodes))
            maburn.exit()
        })

        function getString(str, firstCharacter, lastCharacter) {
            if(str.match(firstCharacter + "(.*)" + lastCharacter) == null){
                return null;
            }else{
                newStr = str.match(firstCharacter + "(.*)" + lastCharacter)[1].trim()
                return(newStr)
            }
        }




}



