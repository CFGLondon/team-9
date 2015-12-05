var progress = new RadialProgressChart('.progress', {
   diameter: 200,
   series: [{
     labelStart: '\uF105',
     value: 0,
     color: {
       linearGradient: {
         x1: '0%',
         y1: '100%',
         x2: '50%',
         y2: '0%',
         spreadMethod: 'pad'
       },
       stops: [{
         offset: '0%',
         'stop-color': '#ffff00',
         'stop-opacity': 1
       }, {
         offset: '100%',
         'stop-color': '#ff0000',
         'stop-opacity': 1
       }]
     }
   }],
   center: function(p) {
     return p + ' %'
   }
 });

 function getRandom(min, max) {
   return Math.random() * (max - min) + min;
 }

 function loop(p) {
   if (p > 100) {
     setTimeout(function() {
       loop(0)
     }, 3000)
   } else {
     progress.update(p);
     setTimeout(function() {
       loop(p + 1)
     }, 90)
   }
 }

 loop(10);
