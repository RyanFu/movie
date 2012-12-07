function createPieChart(title, dataArr, container){
    if(typeof dataArr != 'object'){
        alert('数据不存在');
        return;
    }
    if(typeof container == 'undefined'){
		container = $('#container');
		if(container.size() == 0){
			alert('图表容器不存在');
			return;
		}
	}
    $(container).show();
    var total = 0;
    for(var i=0; i< dataArr.length; i++){
        if(typeof dataArr[i][1] != 'number'){
            continue;
        }
        total += dataArr[i][1];
    }
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: $(container).attr('id'),
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        credits:{
            enabled: false
        },
        title: {
            text: title,
            style:{
        		color: '#4572A7',
            	font: 'bold 18px Verdana, sans-serif'
        	}
        },
        tooltip: {
            formatter: function() {
	        	var dispVal = this.y;
				if(dispVal < 0.01){
					dispVal = (dispVal * 10000).toFixed(2);
	   				dispVal = dispVal + 'E-4';
				}else{
					if(dispVal!=Math.round(dispVal)){
						dispVal = dispVal.toFixed(2);
					}
				}
                return '<b>'+ this.point.name +'</b>: '+ dispVal + ' (' + (this.y/total*100).toFixed(2) + '%)';
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    style: {
                        font: 'normal 13px Verdana, sans-serif'
                    },
                    formatter: function() {
        				var dispVal = this.y;
        				if(dispVal < 0.01){
        					dispVal = (dispVal * 10000).toFixed(2);
               				dispVal = dispVal + 'E-4';
        				}else{
        					if(dispVal!=Math.round(dispVal)){
        						dispVal = dispVal.toFixed(2);
        					}
        				}
                        return '<b>'+ this.point.name +'</b>: '+ dispVal;
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            data: dataArr
        }]
    });
}

function createMultiLineChart(title, dataGroup, container){
    if(typeof dataGroup != 'object'){
        alert('数据不存在');
        return;
    }
    if(typeof container == 'undefined'){
		container = $('#container');
		if(container.size() == 0){
			alert('图表容器不存在');
			return;
		}
	}
    $(container).show();
    var xdatakey='date';
    if(window.chart_x_data_key){xdatakey=chart_x_data_key;};
    var xSize = dataGroup[xdatakey].data.length;
    var xStep = xSize > 10? Math.round(xSize/10):1;
    var multiLineChartOptions = {
        colors: ['#4572A7', '#89A54E', '#80699B', '#3D96AE', '#DB843D', '#92A8CD', '#A47D7C', '#B5CA92', '#AA4643'],
        yAxis: new Array(),
        series: new Array()
    };
    var count = -1;
    for(var p in dataGroup){
        if(typeof dataGroup[p].data == 'undefined'){
            break;
        }
        if(count < 0){
            count++;
            continue;
        }
        multiLineChartOptions.yAxis.push({
            title: {
                text: dataGroup[p].name,
                style: {
                    color: multiLineChartOptions.colors[count],
                    font: 'bold 15px Verdana, sans-serif'
                }
            },
            labels: {
                formatter: function(){
                    return this.value;
                },
                style: {
                    color: multiLineChartOptions.colors[count]
                }
            },
            opposite: count > 0 ? true: false
        });
        multiLineChartOptions.series.push({
            name: dataGroup[p].name,
            type: 'spline',
            data: dataGroup[p].data,
            yAxis: count
        });
        count++;
    }
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: $(container).attr('id'),
            zoomType: 'xy'
        },
        credits:{
            enabled: false
        },
        colors: multiLineChartOptions.colors,
        title:{
            text: title,
            style:{
            	font: 'bold 18px Verdana, sans-serif'
        	}
        },
        symbols: 'circle',
        plotOptions: {
            series:{
                events:{
                    hide: function(){
                        this.yAxis.axisTitle.hide();
                        if(setChartShowCookie){setChartShowCookie(this);}
                    },
                    show: function(){
                        this.yAxis.axisTitle.show();
                        if(setChartShowCookie){setChartShowCookie(this);}
                    }
                },
                marker: {
                    enabled: false,
                    states: {
                        hover: { 
                            enabled: true
                        }
                    }
                }
            }
        },
        xAxis: [{
            categories: dataGroup[xdatakey].data,
            labels: {
                rotation: -45,
                align: 'right',
                step: xStep
            }
        }],
        yAxis: multiLineChartOptions.yAxis,
        series: multiLineChartOptions.series
    });
    showChartSeries(chart);
    return chart;
}
function createMultiBarChart(title, dataGroup, container){
    if(typeof dataGroup != 'object'){
        alert('数据不存在');
        return;
    }
    if(typeof container == 'undefined'){
		container = $('#container');
		if(container.size() == 0){
			alert('图表容器不存在');
			return;
		}
	}
    $(container).show();
    var multiLineChartOptions = {
        colors: ['#4572A7', '#89A54E', '#80699B', '#3D96AE', '#DB843D', '#92A8CD', '#A47D7C', '#B5CA92', '#AA4643'],
        yAxis: new Array(),
        series: new Array()
    };
    var count = -1;
    for(var p in dataGroup){
        if(typeof dataGroup[p].data == 'undefined'){
            break;
        }
        if(count < 0){
            count++;
            continue;
        }
        multiLineChartOptions.yAxis.push({
            title: {
                text: dataGroup[p].name,
                style: {
                    color: multiLineChartOptions.colors[count],
                    font: 'bold 15px Verdana, sans-serif'
                }
            },
            labels: {
                formatter: function(){
                    return this.value;
                },
                style: {
                    color: multiLineChartOptions.colors[count]
                }
            },
            opposite: count > 0 ? true: false
        });
        multiLineChartOptions.series.push({
            name: dataGroup[p].name,
            type: 'column',
            data: dataGroup[p].data,
            yAxis: count
        });
        count++;
    }
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: $(container).attr('id'),
            defaultSeriesType: 'column'
        },
        credits:{
            enabled: false
        },
        colors: multiLineChartOptions.colors,
        title:{
            text: title,
            style:{
            	font: 'bold 18px Verdana, sans-serif'
        	}
        },
        plotOptions: {
            series:{
                events:{
                    hide: function(){
                        this.yAxis.axisTitle.hide();
                    },
                    show: function(){
                        this.yAxis.axisTitle.show();
                    }
                },
                marker: {
                    enabled: false,
                    states: {
                        hover: { 
                            enabled: true
                        }
                    }
                }
            },
	        column: {
	                pointPadding: 0.2,
	                borderWidth: 0
	        }        
        },
        xAxis: [{
            categories: dataGroup["name"].data,
        }],
        yAxis: multiLineChartOptions.yAxis,
        series: multiLineChartOptions.series
    });
    return chart;
}
function createBarChart(title, dataArr, container, yAxisText){
    if(typeof dataArr != 'object'){
        alert('数据不存在');
        return;
    }
    if(typeof container == 'undefined'){
		container = $('#container');
		if(container.size() == 0){
			alert('图表容器不存在');
			return;
		}
	}
    $(container).show();
    var categories = new Array();
    var data = new Array();
    for(var i=0; i<dataArr.length; i++){
        categories.push(dataArr[i][0]);
        data.push(dataArr[i][1]);
    }
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: $(container).attr('id'),
            defaultSeriesType: 'column',
            margin: [ 50, 50, 100, 80]
        },
        credits:{
            enabled: false
        },
        title: {
            text: title,
            style:{
        		color: '#4572A7',
            	font: 'bold 18px Verdana, sans-serif'
        	}
        },
        xAxis: {
            categories: categories,
            labels: {
                rotation: -45,
                align: 'right',
                style: {
        			color: '#0C0C0C',
                    font: 'normal 15px Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: yAxisText,
                style:{
        			color: '#000000',
        			font: 'bold 15px Verdana, sans-serif'
        		}
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.x +'</b><br/>: ' + Highcharts.numberFormat(this.y, 2);
            }
        },
            series: [{
            name: title,
            data: dataArr,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: -3,
                y: 10,
                formatter: function() {
                    return this.y.toFixed(2);
                },
                style: {
                    font: 'normal 15px Verdana, sans-serif'
                }
            }           
        }]
    });
 }
function setChartShowCookie(wrap){
	var ss="_";
    for (var i=0; i< wrap.chart.series.length; i++){
        if(wrap.chart.series[i].visible){
        	ss=ss+i+"_";
        }
    }
    var exp=new Date("December 31, 2015");
    document.cookie ="ChartShowCookie="+ss+";expires=" + exp.toGMTString();
}
function showChartSeries(chart){
    var arr = document.cookie.match(new RegExp("(^| )ChartShowCookie=([^;]*)(;|$)"));
    var ss="_0_";
    if(arr && arr[2]){ss=arr[2];}
    for (var i=0; i< chart.series.length; i++){
        if(ss.indexOf(i+"")>-1){
        	chart.series[i].show();
        }else{
        	chart.series[i].hide();
        }
    }    
}