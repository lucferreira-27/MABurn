



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
        maburn.waitElement('.single-chapter').then(()=>{

                document.querySelectorAll('.single-chapter a').forEach(el =>{
                    values.push({name : el.title, url: el.href})
                })
                maburn.send("RESULT", JSON.stringify(values))
                maburn.exit()
        })
    else{
        maburn.waitElement('.image-navigator').then(()=>{
            values.push({name: target, url: collect(), type: 'manga' });
            maburn.send("RESULT", JSON.stringify(values))
            maburn.exit()
        })
    }
    function collect() {
        let pages = []
        document.querySelectorAll('.image-navigator .slideit').forEach((el, index) =>{
            pages.push(({page : index + 1, image : el.src}))
        })
        return pages
    }



}



