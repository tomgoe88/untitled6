/**
 * Created by Jutom on 17.01.2017.
 */
function test(eevent) {

    var test = ' '+eevent.title;
    //document.getElementsByName('tec').value= JSON.stringify(test);//hier ist zumindest schon mal der Wert angekommen, also über name zu suchen ist super
    sendEvent(test);
    PF('testVar').show();
}

