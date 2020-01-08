/*
S.Sams Lifexperience
-----------------------------------------------------
Copyright (C) 2002 - 2008 S.Sams Lifexperience!
All rights reserved
Email:		Cassams@gmail.com / S.Sams@msn.com
WebSite:	Http://lab.travelive.com.cn/
Msn:		S.Sams@Msn.com
Author:		Sam Shen
*/

eval(function(p,a,c,k,e,d){e=function(c){return(c<a?"":e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1;};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p;}('(1($){$.k.j=1(I){3 t=0;3 s=$.H({},$.k.j.E,I);$(\'R\').Q(\'<q V="7"></q>\');$(U).T(1(){$(\'.7\').u()});0.y(1(){8($(0).9(\'x\')!=v){$(0).P(1(){$(\'.7\').K({L:$.J(0)+\'r\',O:$.C(0)+\'r\'});$(\'.7\').M($(0).9(\'x\'));$(\'.7\').13("12")},1(){$(\'.7\').u()})}8($(0).9(\'l\')!=v){$(0).15(1(){$(0).c(\'d\')}).14(1(){8($(0).9(\'X\')==\'f\'){0.a=0.a.W()}3 e=z w($(0).9(\'l\'));8(e.D(0.a)){$(0).c(\'d\').h(\'i\')}11{$(0).c(\'i\').h(\'d\')}})}});8(s.m){$(\'Z\').Y(1(){3 p=f;t.y(1(){3 e=z w($(0).9(\'l\'));8(!e.D(0.a)){$(0).c(\'i\').h(\'d\');p=N}});g p})}};$.H({16:1(6){g 6.10},J:1(6){3 2=6;3 4,n=2.G;B(2.b!=A){4=2.b;n+=4.G;2=4}g n},C:1(6){3 2=6;3 4,o=2.F;B(2.b!=A){4=2.b;o+=4.F;2=4}g o+$(6).17()+5},m:f});$.k.j.E={m:f}})(S);',62,70,'this|function|go|var|oParent||object|tooltipshowpanel|if|attr|value|offsetParent|removeClass|tooltipinputerr|thisReg|true|return|addClass|tooltipinputok|tooltip|fn|reg|onsubmit|oLeft|oTop|isSubmit|div|px|opts|getthis|hide|undefined|RegExp|tip|each|new|null|while|getTop|test|defaults|offsetTop|offsetLeft|extend|options|getLeft|css|left|html|false|top|hover|append|body|jQuery|mouseover|document|class|toUpperCase|toupper|submit|form|offsetWidth|else|fast|fadeIn|blur|focus|getWidth|height'.split('|'),0,{}));


(function($) {
    $(document).ready(function() {
        $('select[tip],select[reg],input[tip],input[reg],textarea[tip],textarea[reg]').tooltip();
    });
})(jQuery);