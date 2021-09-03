
let values = []

function execute(target) {
    if (maburn.active) {
        console.log("Maburn already active!")
        return
    }

    if(!target){
        maburn.screenshot()
        maburn.waitElement('#chapterList').then(async ()=>{
            Array.from(document.querySelectorAll("#chapterList a"))
                .filter(a => a.className.length === 0)
                .forEach(a => {
                    values.push({name : a.textContent, url : a.href})
                })
            maburn.send("RESULT",JSON.stringify(values))
            maburn.exit()
        })
    }else {
        maburn.waitElement('.column img').then(async () => {
            getAllImages().then(images =>{
                values.push({name: target, url: collect(images), type: 'manga' });
                maburn.send("RESULT", JSON.stringify(values))
                maburn.exit()
            })

            function collect(images){
                let pages = []
                images.forEach((url, index) => {
                    pages.push({ page: index + 1, image: url })
                })
                return pages
            }

           async function getAllImages() {

                let tempImages = [];
                let finalImages = []
               await scrollToBottomAndWait(1000)
                document
                    .querySelectorAll('.column img')
                    .forEach(image => tempImages.push(image))
               let lastImage = tempImages[tempImages.length - 1] ;

              await maburn.waitUntil(() => !lastImage.src.includes(".gif")).then(async () =>{
                   await getLastImageIndex(lastImage.src).then(index =>{
                       const src = substringImageSource(lastImage.src)[1];
                       const type = substringImageSource(lastImage.src)[2];

                       for(let i = 1; i <= index; i++){
                           let imageSrc = src + "_" + i + type;
                           finalImages.push(imageSrc)
                       }
                   })

               })



               return finalImages;

            }
            async  function getLastImageIndex(lastImage){
                await scrollToBottomAndWait(500)
                return parseInt(substringImageSource(lastImage)[0]);
            }
            function substringImageSource(image){
                let index =  image.lastIndexOf("_");
                return [image.substring(index + 1, image.lastIndexOf(".")),
                    image.substring(0,index),
                    image.substring(image.lastIndexOf("."))
                ]
            }


            async function scrollToBottomAndWait(time) {
                window.scrollTo(0, document.body.scrollHeight);
                await maburn.sleep(time)
                window.scrollTo(0, document.body.scrollHeight);
                await maburn.sleep(time)
            }


        })
    }
        maburn.waitElement('.mirror_link').then(async ()=>{
            const listDownloadsOptions = document.querySelectorAll('div.mirror_link')[0].querySelectorAll('div');
            const bestOption = listDownloadsOptions[listDownloadsOptions.length - 1];
            const link = bestOption.querySelector('a').href;
            episodes.push({name : target, url : link})
            maburn.send('RESULT',JSON.stringify(episodes))
            maburn.exit()
        })






}



