// 심리 점수
const scoreBox = document.querySelector(".info-score");
const mindScore = scoreBox.querySelector("p");
const score = parseFloat(mindScore.innerHTML);

if (score >= 4) scoreBox.classList.add("green");
else if (score >= 2) scoreBox.classList.add("yellow");
else if (score >= 0) scoreBox.classList.add("red");



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

// 기간별 보기의 유형 필터 동작
const typeFilters = typeFilterContainer.querySelectorAll(".type-filter");

typeFilters.forEach((typeFilter, idx) => {

    typeFilter.addEventListener("click", () => {

        typeFilters.forEach(each => {
            each.classList.remove("active");
        });

        typeFilters[idx].classList.add("active");

    });

});

// 유형 차트 아이템 동작
const typeChartItems = document.querySelectorAll(".type-chart-item");

typeChartItems.forEach(item => {

    item.addEventListener("click", () => {
        item.querySelector(".item-info i").classList.toggle("active");
        item.classList.toggle("active");
    });

    const colorStatus = item.querySelector(".status");
    const itemAverage = item.querySelector(".item-average");
    const average = parseFloat(itemAverage.innerHTML);

    if (average >= 4) colorStatus.classList.add("green");
    else if (average >= 2) colorStatus.classList.add("yellow");
    else if (average >= 0) colorStatus.classList.add("red");

});

/////////// 메인 차트 ///////////
const radarChart = document.getElementById("chart-radar");
const lineChart = document.getElementById("chart-line");

/////// 라벨 ///////

// 라벨 - 메인
const typeLabels = ["경기력", "불안감", "훈련", "기능 자신감", "부상", "관계", "진로", "학업", "생활"];
const monthLabels = ["1월", "2월", "3월", "4월", "5월", "6월"];
const dayLabels = ["05.01", "05.13", "05.18", "05.20"];
// 라벨 - 유형별
const elementLabels = ["요인 1", "요인 2", "요인 3", "요인 4", "요인 5"];
const compareAgeLabel = ["내 데이터", "나이 평균"];
const comparePositionLabel = ["내 데이터", "포지션 평균"];


/////// 데이터 ///////

// 데이터 - 메인
const myAvgData = [4, 4, 4, 3, 2, 1, 2, 2, 3];
const avgByAgeData = [3, 3, 3, 3, 3, 3, 3, 3, 3];
const avgByPositionData = [4, 4, 4, 4, 4, 4, 4, 4, 4];
const monthData = [2.3, 4.5, 3.7, 2.5, 3.8, 3.6];
const dayData = [2.8, 3.3, 2.5, 3.9];
// 데이터 - 유형별
const byTypeData = [4, 3, 2, 1, 4];
const compareData = [4, 3];


/////// 데이터 세트 ///////

// 데이터 세트
const myDatasets = {
    label: '내 심리상태',
    data: myAvgData,
    fill: true,
    backgroundColor: 'rgba(255,228,0,0.2)',
    borderColor: '#FFE400',
    borderWidth: 1
};

const ageDatasets = {
    label: '나이 평균',
    data: avgByAgeData,
    fill: false,
    backgroundColor: 'white',
    borderColor: 'blue',
    borderWidth: 2
};

const positionDatasets = {
    label: '포지션별 평균',
    data: avgByPositionData,
    fill: false,
    backgroundColor: 'white',
    borderColor: 'blue',
    borderWidth: 2
};

const monthDatasets = {
    data: monthData,
    borderColor: '#FFE400',
    borderWidth: 1,
    pointRadius: 5,
    pointBackgroundColor: '#FFE400',
};

const dayDatasets = {
    data: dayData,
    borderColor: '#FFE400',
    borderWidth: 1,
    pointRadius: 5,
    pointBackgroundColor: '#FFE400',
};

const byTypeDatasets = {
    label: '심리 상태',
    data: byTypeData,
    backgroundColor: byTypeData.map(function (value) {
        if (value >= 4) {
            return '#CDF3E6';
        } else if (value >= 2) {
            return '#F5ECD7';
        } else {
            return '#FFE2DC';
        }
    }),
    borderColor: byTypeData.map(function (value) {
        if (value >= 4) {
            return '#1DBA84';
        } else if (value >= 2) {
            return '#EFB93A';
        } else {
            return '#FC5230';
        }
    }),
    borderWidth: 1
};

const compareDatasets = {
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

// 평균 보기 - 내 평균
const data_myAvg = {
    labels: typeLabels,
    datasets: [myDatasets]
};

// 평균 보기 - 나이별 평균
const data_byAge = {
    labels: typeLabels,
    datasets: [myDatasets, ageDatasets]
};

// 평균 보기 - 포지션별 평균
const data_byPosition = {
    labels: typeLabels,
    datasets: [myDatasets, positionDatasets]
};

// 기간별 보기 - 월별 평균
const data_byMonth = {
    labels: monthLabels,
    datasets: [monthDatasets]
};

// 기간별 보기 - 주별 평균
const data_byDay = {
    labels: dayLabels,
    datasets: [dayDatasets]
};

// 유형별 차트
const data_byType = {
    labels: elementLabels,
    datasets: [byTypeDatasets]
};

// 유형별 상세
const data_compareAge = {
    labels: compareAgeLabel,
    datasets: [compareDatasets]
};
const data_comparePosition = {
    labels: comparePositionLabel,
    datasets: [compareDatasets]
};


/////// 차트 Configuration ///////

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

// 라인 차트
const config_line = {
    type: 'line',
    data: data_byMonth,
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
                    return value;
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

// 바 차트 - 유형별
const config_bar = {
    type: 'bar',
    data: data_byType,
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
const dateChart = new Chart(lineChart, config_line);
const typeDetailCharts = [];

for (var i = 0; i < 9; i++) {
    new Chart(document.getElementById("chart-by-type" + i), config_bar);
    typeDetailCharts[i] = new Chart(document.getElementById("type-detail-item" + i), config_bar_avg);
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

function dateFilterChange() {
    var selected = dateFilter.options[dateFilter.selectedIndex].value;
    if (selected === "byMonth") config_line.data = data_byMonth;
    if (selected === "byWeek") config_line.data = data_byDay;
    dateChart.update();
}

function updateTypeDetailCharts(selected) {
    if (selected === 0) config_bar_avg.data = data_compareAge;
    if (selected === 1) config_bar_avg.data = data_comparePosition;
    for (var i in typeDetailCharts) {
        typeDetailCharts[i].update();
    }
}


