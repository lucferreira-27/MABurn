



let values = []
function execute(target) {
    if (maburn.active) {
        console.log("Maburn already actived!")
        return
    }
    if (!target){
           maburn.screenshot()
    }   
    if(!target)
        maburn.waitElement('.chapters').then(()=>{
                    document.querySelectorAll('.chapters .cap .card .pop-content .tags').forEach(el => {
                        var cap = el.querySelector('a');
                        values.push({name: cap.title, url: cap.href })
                    })
                    maburn.send("RESULT", JSON.stringify(values))
                    maburn.exit()
        })
    else
        maburn.waitElement('#slider').then(()=>{
            values.push({name: target, url: collect(), type: 'manga' });
            maburn.send("RESULT", JSON.stringify(values))
            maburn.exit()
        })

    function collect() {
        let pages = []
        document.querySelectorAll('#slider a img').forEach((e, index) => {
            pages.push({ page: index + 1, image: e.src })
        })
        return pages
    }



}



