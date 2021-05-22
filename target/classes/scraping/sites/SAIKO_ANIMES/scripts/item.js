(query) => {

    function getVideoDownloadLink(index){
        var videoQuery = 'video source';
        var table = document.querySelector('.material-table tbody');
        
        table.rows[index].click();
        setTimeout(() => {  console.log("World!"); }, 1000);
        document.querySelector(videoQuery).src;
    }

    var videoQuery = 'video source';
    var table = document.querySelector('.material-table tbody');
    
    table.rows[0].click();
    setTimeout(() => {  console.log("World!"); }, 1000);
    document.querySelector(videoQuery).src;

    return episodio;
}
