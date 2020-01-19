$(document).ready(function(){
	'use strict';

	Highcharts.chart('crypt-prc-chrt', {
	    chart: {
	        type: 'pie',
	        options3d: {
	            enabled: true,
	            alpha: 45
	        },
	        backgroundColor: null
	    },
	    title: {
	        text: null
	    },
	    subtitle: {
	        text: null
	    },
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            innerSize: 100,
	            depth: 45,
	            dataLabels: {
	                enabled: true,
	                format: '{point.name}'
	            }
	        },
	        series: {
	            dataLabels: {
	                color: '#fff',
	                textSize: 30
	            },
	            marker: {
	                lineColor: '#fff'
	            }
	        },
	        boxplot: {
	            fillColor: '#fff'
	        },
	        candlestick: {
	            lineColor: 'white'
	        },
	        errorbar: {
	            color: 'white'
	        }
	    },
	    credits: {
	        enabled: false
	    },
	    series: [{
	        name: 'Delivered amount',
	        data: [
	            {
	                name: 'Firefox',
	                y: 45.0,
	                sliced: true,
	                selected: true
	            },
	            ['IE', 26.8],
	            ['Chrome', 12.8],
	            ['Safari', 8.5],
	            ['Opera', 6.2],
	            ['Others', 0.7]
	        ]
	    }]
	});
});