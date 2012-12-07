function loadMap(){ //��Ҫȫ�ֱ���dataKeyList
    window.$numTrim = function(str){
        var trimStr = $.trim(str);
        if('' == trimStr){
            trimStr = '0';
        }
        return trimStr;
    };
    //��ʼ��dataMapStatus
    var totalLineNum = $('div.report table tr:visible').size()-2;
    window.dataMapStatus = {
        maxSize: -1,	//ͳ�Ƶ��ƹ������,-1��ʾͳ��ȫ��
        isFull: false,
        totalLineNum: totalLineNum,
        countedLineNum: 0,
        isOtherNeeded: false
    };
    window.statisticDataFromPage = {};
    window.dataMap = new Map();
    //ȷ������������ڱ����������������
    $('div.report th:visible').each(function(index){
        for(var dataKey in dataKeyList ){
            if($.trim($(this).text()) == dataKeyList[dataKey].name){
                dataKeyList[dataKey].index = index;
                return;
            }
        }
    });
    //if(dataKeyList['date'].index < 0)���򲻴���date��
    $('div.report table tr:last').each(function(){
        $(this).children('td:visible').each(function(index){
            for(var dataKey in dataKeyList){
                if(index == dataKeyList[dataKey].index){
                	var tempStoreValue = '';
                    if('int' == dataKeyList[dataKey].expectedType){
                    	tempStoreValue = parseInt($numTrim($(this).text()));
                        dataKeyList[dataKey].tempStore = isNaN(tempStoreValue)?0:tempStoreValue;
                    }else if('float' == dataKeyList[dataKey].expectedType){
                    	tempStoreValue = parseFloat($numTrim($(this).text()));
                        dataKeyList[dataKey].tempStore = isNaN(tempStoreValue)?0:tempStoreValue;
                    }else{
                        dataKeyList[dataKey].tempStore = $(this).text();
                    }
                    return true;
                }
            }
        });
        for(var dataKey in dataKeyList){
            if('date' == dataKey){
                continue;
            }
            //�������һ�еĺϼ����ݼ�
            statisticDataFromPage[dataKey] = {};
            if('ctr' == dataKey ){
                statisticDataFromPage[dataKey].avg = dataKeyList[dataKey].tempStore;
                statisticDataFromPage[dataKey].sum = -1;
            }else if('avgClickPrice' == dataKey){
                statisticDataFromPage[dataKey].avg = dataKeyList[dataKey].tempStore;
                statisticDataFromPage[dataKey].sum = -1;
            }else{
                statisticDataFromPage[dataKey].sum = dataKeyList[dataKey].tempStore;
                statisticDataFromPage[dataKey].avg = statisticDataFromPage[dataKey].sum / dataMapStatus.totalLineNum;
            }
        }
    });
    //��ȡ����е�������
    $('div.report table tr').each(function(){
        if(dataMapStatus.isFull){
            return false;
        }
        if($(this).children('th').size() > 0){
            return true;
        }
        var key = '';
        //��ȡ��ǰ����ÿ�е�����
        $(this).children('td:visible').each(function(index){
            for(var dataKey in dataKeyList){
                if(index == dataKeyList[dataKey].index){
                    if('int' == dataKeyList[dataKey].expectedType){
                        dataKeyList[dataKey].tempStore = parseInt($numTrim($(this).text()));
                    }else if('float' == dataKeyList[dataKey].expectedType){
                        dataKeyList[dataKey].tempStore = parseFloat($numTrim($(this).text()));
                    }else{
                        dataKeyList[dataKey].tempStore = $(this).text();
                    }
                    return true;
                }
            }
            if(index > dataKeyList['date'].index && dataKeyList['pv'].index){
                key += '-' + $.trim($(this).text());
                return true;
            }
        });
        //����ϼ�����
        if(dataKeyList['date'].index > -1 && !/[\d+-]+\d/.test(dataKeyList['date'].tempStore)){
            return false;
        }
        if(/^-+$/.test(key)){
        	return false;
        }
        if(key.length > 1){
            key = key.substring(1);
        }else{
            key = 'ͳ�ƽ��';
        }
        dataMapStatus.countedLineNum++;
        
        //���Ӧ���ƹ������뵱ǰ�е�����
        var dataGroup = dataMap.get(key);
        if(typeof dataGroup == 'undefined'){
            if(dataMapStatus.maxSize > 0 && dataMap.size() >= dataMapStatus.maxSize){
                dataMapStatus.isFull = true;
                return;
            }
            dataGroup = {};
            for(var dataKey in dataKeyList){
                dataGroup[dataKey] = {
                    name: dataKeyList[dataKey].name,
                    data: new Array()
                };
            }
            dataGroup.appendPoint = function(obj, point){
                obj.data.push(point);
            }
            dataMap.put(key, dataGroup);
        }
        for(var dataKey in dataKeyList){
            dataGroup.appendPoint(dataGroup[dataKey], dataKeyList[dataKey].tempStore); 
        }
    });
}

function sortMapData(){
    if(typeof window.dataMap == 'undefined'){
        return false;
    }
    if(dataMap.size() > 1){
        //�Բ�ͬ�ƹ�������ѭ��
        var keys = dataMap.keys();
        for(var i=0; i<keys.length; i++){
            var key = keys[i];
            var dataGroup = dataMap.get(key);
            //��ͬһ�ƹ�����ڲ�ͬ������ָ�����ѭ��
            for(var dataKey in dataGroup){  //dataGroup��dataKey��dataKeyListһ��(ǰ�߸��ݺ�������)
                if(typeof dataGroup[dataKey].data == 'undefined'){
                    continue;
                }
                if(dataKey == 'date'){
                    continue;
                }
                if('ctr' == dataKey){   //���ָ�겻��Ҫsum
                    dataGroup[dataKey].sum = -1;
                }else{
                    var sum = 0;
                    for(var j=0; j<dataGroup[dataKey].data.length; j++){
                        sum += dataGroup[dataKey].data[j];
                    }
                    dataGroup[dataKey].sum = sum;
                }
                //�����ָ���ͳ��ƽ��ֵ
                if('ctr' == dataKey){//ctr=click/pv
                	var avgVal = dataGroup['click'].sum/dataGroup['pv'].sum;
                	dataGroup[dataKey].avg = avgVal == Infinity? 0: avgVal;
                }else if ('avgClickPrice' == dataKey){
                    var notZeroCountNum = 0;
                    for(var k=0; k< dataGroup[dataKey].data.length; k++){
                        if(dataGroup[dataKey].data[k] > 0){
                            notZeroCountNum++;
                        }
                    }
                    dataGroup[dataKey].notZeroCountNum = notZeroCountNum;
                    if(notZeroCountNum > 0){
                        dataGroup[dataKey].avg = dataGroup[dataKey].sum/notZeroCountNum;
                    }else{
                        dataGroup[dataKey].avg = 0;
                    }
                }else{
                    dataGroup[dataKey].avg = dataGroup[dataKey].sum/dataGroup[dataKey].data.length;
                }
            }
        }
        //��ʼ�������Ľ����
        window.sortedDataResults = {
            dispNum: 15
        };
        for(var dataKey in dataKeyList){
            if('date' == dataKey){
                continue;
            }
            //��highcharts��������ݸ�ʽ������װ�����ƹ����ĵ�ǰͳ��ָ��(sum or avg)��������
            var sortedArr = new Array();    
            for(var i=0; i<keys.length; i++){
                var key = keys[i];
                var dataGroup = dataMap.get(key);
                if('ctr' == dataKey){
                    sortedArr.push([key, dataGroup[dataKey].avg]);
                }else if('avgClickPrice' == dataKey) {
                    sortedArr.push([key, dataGroup[dataKey].avg]);
                }else{
                    sortedArr.push([key,dataGroup[dataKey].sum]);
                }
            }
            sortedArr.sort(function(a, b){
                return b[1] - a[1];
            });
            sortedArr = sortedArr.splice(0,sortedDataResults.dispNum);
            //if(dataMapStatus.isOtherNeeded){
                if(dataMap.size() > sortedDataResults.dispNum){
                    var dispSum = 0;    //��ͼչʾ��������ݵ��ۼ�
                    for(var i=0; i<sortedDataResults.dispNum; i++){
                        if(typeof sortedArr[i][1] != 'number'){
                            continue;
                        }
                        if('ctr' == dataKey || 'avgClickPrice' == dataKey){
                        }else{
                            dispSum += sortedArr[i][1];
                        }
                    }
                    var otherSum = statisticDataFromPage[dataKey].sum - dispSum;
                    statisticDataFromPage[dataKey].otherSum = otherSum;
                    if('avgClickPrice' == dataKey){
                        sortedArr.push(['����',statisticDataFromPage['consume'].otherSum/statisticDataFromPage['click'].otherSum]);
                    }else if ('ctr' == dataKey){
                        sortedArr.push(['����',statisticDataFromPage['click'].otherSum/statisticDataFromPage['pv'].otherSum]);
                    }else{
                        sortedArr.push(['����',otherSum]);    //д�ɶ�ά������Ϊ����Ӧpie chart��data�ĸ�ʽ
                    }
                }
            //}
            sortedDataResults[dataKey] = sortedArr;
        }
    }
    return true;
}