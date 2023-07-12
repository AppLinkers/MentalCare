/////////// 메인 차트 ///////////
const radarChart = document.getElementById("chart-radar");
const lineChart = document.getElementById("chart-line");

// 심리 점수
const scoreBox = document.querySelector(".info-score");
const mindScore = scoreBox.querySelector("p");
const score = parseFloat(mindScore.innerHTML);

if (score >= 4) scoreBox.classList.add("green");
else if (score >= 2) scoreBox.classList.add("yellow");
else if (score >= 0) scoreBox.classList.add("red");

////////////// 데이터 //////////////

// 내 데이터 - 세부 데이터 포함
const myData = [
    [4, 3, 2, 1, 4],
    [5, 3, 3, 5, 4],
    [2, 3, 2, 1, 1],
    [4, 3, 2, 1, 4],
    [5, 3, 3, 5, 4],
    [2, 3, 2, 1, 1],
    [4, 3, 2, 1, 4],
    [5, 3, 3, 5, 4],
    [2, 3, 2, 1, 1]
];

// 내 평균 데이터
const myAvgData = calculateAverageForEachIndex(myData);

const avgByAgeData = [3, 3, 3, 3, 3, 3, 3, 3, 3];
const avgByPositionData = [4, 4, 4, 4, 4, 4, 4, 4, 4];

const compareAgeData = mergeArraysByIndex(myAvgData, avgByAgeData);
const comparePositionData = mergeArraysByIndex(myAvgData, avgByPositionData);

function calculateAverageForEachIndex(data) {
    var averages = data.map(function (numbers) {
        if (numbers.length === 0) {
            return 0;
        }

        var sum = numbers.reduce(function (acc, val) {
            return acc + val;
        }, 0);

        var average = sum / numbers.length;
        return average;
    });

    return averages;
};

function mergeArraysByIndex(array1, array2) {
    var mergedArray = [];
    for (var i = 0; i < array1.length; i++) {
        var pair = [array1[i], array2[i]];
        mergedArray.push(pair);
    }
    return mergedArray;
};

// 탭 동작 (평균 보기, 기간별 보기)
const tabs = document.querySelectorAll(".chart-tab p");
const filters = document.querySelectorAll(".filter-box");
const canvases = document.querySelectorAll(".canvas__container");
const typeDetailContainer = document.querySelector(".type-detail__container");
const typeFilterContainer = document.querySelector(".type-filter__container");

tabs.forEach((tab, idx) => {

    tab.addEventListener("click", () => {

        tabs.forEach(each => {
            each.classList.remove("active");
        });

        filters.forEach(each => {
            each.classList.remove("active");
        });

        canvases.forEach(each => {
            each.classList.remove("active");
        });

        typeDetailContainer.classList.remove("active");
        typeFilterContainer.classList.remove("active");


        tabs[idx].classList.add("active");
        filters[idx].classList.add("active");
        canvases[idx].classList.add("active");
        if (idx === 0) typeDetailContainer.classList.add("active");
        if (idx === 1) typeFilterContainer.classList.add("active");

    });

});

// 평균 보기의 유형별 상세 데이터 동작
const typeDetailHeader = typeDetailContainer.querySelector(".type-detail-header");
const typeDetailBody = typeDetailContainer.querySelector(".type-detail-body");
const typeDetailIcon = typeDetailContainer.querySelector("i");

typeDetailHeader.addEventListener("click", () => {
    typeDetailBody.classList.toggle("active");
    typeDetailIcon.classList.toggle("active");
});

////////////////////////////////////////////////////////////////////////
/// 기간별 보기
////////////////////////////////////////////////////////////////////////

const monthData = [
    [2.3, 4.5, 3.7, 2.5, 3.8, 3.6],
    [3.1, 2.4, 4.0, 1.7, 2.2, 3.8],
    [4.3, 2.5, 1.7, 3.5, 2.8, 4.6],
    [2.3, 4.5, 3.7, 2.5, 3.8, 3.6],
    [3.1, 2.4, 3.0, 1.7, 2.2, 3.8],
    [4.3, 2.5, 1.7, 3.5, 2.8, 4.6],
    [2.3, 4.5, 3.7, 2.5, 3.8, 3.6],
    [3.1, 2.4, 2.0, 1.7, 2.2, 3.8],
    [4.3, 2.5, 1.7, 3.5, 2.8, 4.6],
];

const monthLabels = ["1월", "2월", "3월", "4월", "5월", "6월"];

const config_line = makeLineConfig(makeDateData(makeDateDatasets(monthData[0])));
const dateChart = new Chart(lineChart, config_line);
const dateDataList = [];

// 기간별 보기의 유형 필터 동작
const typeFilters = typeFilterContainer.querySelectorAll(".type-filter");

typeFilters.forEach((typeFilter, idx) => {

    dateDataList[idx] = makeDateData(makeDateDatasets(monthData[idx]));

    typeFilter.addEventListener("click", () => {
        typeFilters.forEach(each => {
            each.classList.remove("active");
        });
        config_line.data = dateDataList[idx];
        dateChart.update();
        typeFilters[idx].classList.add("active");
    });
});

function makeDateDatasets(data) {
    return {
        data: data,
        borderColor: '#FFE400',
        borderWidth: 1,
        pointRadius: 5,
        pointBackgroundColor: '#FFE400',
    };
};

function makeDateData(datasets) {
    return {
        labels: monthLabels,
        datasets: [datasets]
    };
};

function makeLineConfig(data) {
    return {
        type: 'line',
        data: data,
        plugins: [ChartDataLabels],
        options: {
            scales: {
                x: {
                    offset: true,
                    ticks: {
                        padding: 10,
                        font: {
                            size: 14,
                            weight: 'bold'
                        }
                    }
                },
                y: {
                    beginAtZero: true,
                    max: 5,
                    ticks: {
                        stepSize: 1,
                        font: {
                            size: 14
                        }
                    }
                }
            },
            plugins: {
                legend: {
                    display: false
                },
                datalabels: {
                    formatter: function (value) {
                        return value.toFixed(1);
                    },
                    align: 'top',
                    anchor: 'center',
                    padding: 10,
                    font: {
                        size: 14,
                        weight: 'bold'
                    },
                },
            }
        }
    };
};
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////

// 유형 차트 아이템 동작
const typeChartItems = document.querySelectorAll(".type-chart-item");

typeChartItems.forEach((item, index) => {
    item.addEventListener("click", () => {
        item.querySelector(".item-info i").classList.toggle("active");
        item.classList.toggle("active");
    });
    const colorStatus = item.querySelector(".status");
    const itemAverage = item.querySelector(".item-average");
    const average = calculateAverage(myData[index]);
    itemAverage.innerHTML = average;
    if (average >= 4) colorStatus.classList.add("green");
    else if (average >= 2) colorStatus.classList.add("yellow");
    else if (average >= 0) colorStatus.classList.add("red");

});

// 유형별 차트 평균 구하기
function calculateAverage(numbers) {
    if (numbers.length === 0) {
        return 0;
    }

    var sum = numbers.reduce(function (acc, val) {
        return acc + val;
    }, 0);

    var average = sum / numbers.length;
    return average.toFixed(1);
};

/////// 라벨 ///////

// 라벨 - 메인
const typeLabels = ["경기력", "불안감", "훈련", "기능 자신감", "부상", "관계", "진로", "학업", "생활"];
const dayLabels = ["05.01", "05.13", "05.18", "05.20"];

// 라벨 - 유형별
const elementLabels = ["요인 1", "요인 2", "요인 3", "요인 4", "요인 5"];

/////// 데이터 세트 ///////

// 데이터 세트
const myDatasets = {
    label: '내 심리상태',
    data: myAvgData,
    fill: true,
    backgroundColor: 'rgba(101, 39, 190, 0.3)',
    borderColor: '#6527BE',
    borderWidth: 2
};

//////////////////// 평균 차트 (나이, 포지션) //////////////////////
function makeAvgDatasets(labelText, data) {
    return {
        label: labelText,
        data: data,
        fill: false,
        backgroundColor: 'white',
        borderColor: '#F86F03',
        borderWidth: 3
    };
};

// 평균 보기 - 내 평균
const data_myAvg = {
    labels: typeLabels,
    datasets: [myDatasets]
};

// 평균 보기 - 나이별 평균
const data_byAge = {
    labels: typeLabels,
    datasets: [myDatasets, makeAvgDatasets('나이 평균', avgByAgeData)]
};

// 평균 보기 - 포지션별 평균
const data_byPosition = {
    labels: typeLabels,
    datasets: [myDatasets, makeAvgDatasets('포지션별 평균', avgByPositionData)]
};

//////////////////// 평균 비교 차트 (나이, 포지션) //////////////////////
function makeCompareLabel(compareLabel) {
    return ["내 데이터", compareLabel];
};

function makeCompareDatasets(compareData) {
    return {
        data: compareData,
        backgroundColor: [
            'rgba(255,228,0,0.2)',
            '#F9F9F9'
        ],
        borderColor: [
            '#FFE400',
            '#B5B5B5'
        ],
        borderWidth: 1
    };
};

// 유형별 상세
const data_compareAge = {
    labels: makeCompareLabel("나이 평균"),
    datasets: [makeCompareDatasets(compareAgeData[0])]
};
const data_comparePosition = {
    labels: makeCompareLabel("포지션 평균"),
    datasets: [makeCompareDatasets(comparePositionData[0])]
};

function makeCompareData(label, compareData) {
    return {
        labels: makeCompareLabel(label),
        datasets: [makeCompareDatasets(compareData)]
    };
};



//////////////////// 유형별 차트 //////////////////////
function makeTypeDataColor(data, green, yellow, red) {
    return data.map(function (value) {
        if (value >= 4) {
            return green;
        } else if (value >= 2) {
            return yellow;
        } else {
            return red;
        }
    });
};

function makeTypeDatasets(data) {
    return {
        label: '심리 상태',
        data: data,
        backgroundColor: makeTypeDataColor(data, '#CDF3E6', '#F5ECD7', '#FFE2DC'),
        borderColor: makeTypeDataColor(data, '#1DBA84', '#EFB93A', '#FC5230'),
        borderWidth: 1
    };
};


function makeTypeData(data) {
    return {
        labels: elementLabels,
        datasets: [makeTypeDatasets(data)]
    };
};
//////////////////// 차트 Configuration //////////////////////

// 바 차트 config
function makeBarConfig(data) {
    return {
        type: 'bar',
        data: data,
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    max: 5,
                    ticks: {
                        stepSize: 1
                    }
                }
            },
            plugins: {
                legend: {
                    display: false
                },
            },
        }
    };
};

// 레이더 차트
const config_radar = {
    type: 'radar',
    data: data_myAvg,
    options: {
        elements: {
            line: {
                borderWidth: 4
            },
            point: {
                radius: 0,
            }
        },
        scales: {
            r: {
                angleLines: {
                    display: false
                },
                suggestedMin: 1,
                suggestedMax: 5,
                ticks: {
                    beginAtZero: true,
                    stepSize: 1,
                    callback: function (value) {
                        return Math.round(value);
                    }
                },
                pointLabels: {
                    font: {
                        family: 'Arial',
                        size: 14,
                        weight: 'bold'
                    }
                }
            }
        },
        plugins: {
            legend: {
                display: true,
                position: 'bottom'
            },
        },

    }
};



// 바 차트 - 평균 상세
const config_bar_avg = {
    type: 'bar',
    data: data_compareAge,
    options: {
        scales: {
            y: {
                beginAtZero: true,
                max: 5,
                ticks: {
                    stepSize: 1
                }
            }
        },
        plugins: {
            legend: {
                display: false
            },
        },
    }
};


// 첫 화면: 평균 보기 - 내 평균
const avgChart = new Chart(radarChart, config_radar);
const typeDetailCharts = [];

const compareConfigs = [];
const compareAgeDataList = [];
const comparePositionDataList = [];

for (var i = 0; i < myData.length; i++) {
    new Chart(
        document.getElementById("chart-by-type" + i),
        makeBarConfig(makeTypeData(myData[i]))
    );
    compareAgeDataList[i] = makeCompareData("나이 평균", compareAgeData[i]);
    comparePositionDataList[i] = makeCompareData("포지션 평균", comparePositionData[i]);
    compareConfigs[i] = makeBarConfig(compareAgeDataList[i]);
    typeDetailCharts[i] = new Chart(
        document.getElementById("type-detail-item" + i),
        compareConfigs[i]
    );
}

// 필터
const averageFilter = document.getElementById("average-filter");
const dateFilter = document.getElementById("date-filter");

function avgFilterChange() {

    var selected = averageFilter.options[averageFilter.selectedIndex].value;

    typeDetailContainer.classList.add("show");

    if (selected === "byAge") {
        config_radar.data = data_byAge;
        updateTypeDetailCharts(0);
    }
    if (selected === "byPosition") {
        config_radar.data = data_byPosition;
        updateTypeDetailCharts(1);
    }
    if (selected === "myAvg") {
        config_radar.data = data_myAvg;
        typeDetailContainer.classList.remove("show");
    }
    avgChart.update();
}

function updateTypeDetailCharts(selected) {
    for (var i in typeDetailCharts) {
        if (selected === 0) compareConfigs[i].data = compareAgeDataList[i];
        if (selected === 1) compareConfigs[i].data = comparePositionDataList[i];
        typeDetailCharts[i].update();
    }
}


