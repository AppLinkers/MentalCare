const playerList = document.querySelectorAll(".target-item");

playerList.forEach(playerItem => {

    const status = playerItem.querySelector(".status");
    const scoreText = playerItem.querySelector(".score");
    const score = parseFloat(scoreText.innerHTML);

    if (score >= 4) status.classList.add("green");
    else if (score >= 2) status.classList.add("yellow");
    else if (score >= 0) status.classList.add("red");
    else status.classList.add("gray");

});

// 탭 동작 (평균 보기, 기간별 보기)
const tabs = document.querySelectorAll(".chart-tab p");
const filters = document.querySelectorAll(".filter-box");
const canvases = document.querySelectorAll(".canvas__container");

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

        tabs[idx].classList.add("active");
        filters[idx].classList.add("active");
        canvases[idx].classList.add("active");

    });

});


/////////// 메인 차트 ///////////
const radarChart = document.getElementById("chart-radar");
const lineChart = document.getElementById("chart-line");

// 라벨
const typeLabels = ["경기력", "불안감", "훈련", "기능 자신감", "부상", "관계", "진로", "학업", "생활"];
const monthLabels = ["1월", "2월", "3월", "4월", "5월", "6월"];
const dayLabels = ["05.01", "05.13", "05.18", "05.20"];

// 데이터
const avgByAgeData = [3, 3, 3, 3, 3, 3, 3, 3, 3];
const avgByPositionData = [4, 4, 4, 4, 4, 4, 4, 4, 4];
const monthData = [2.3, 4.5, 3.7, 2.5, 3.8, 3.6];
const dayData = [2.8, 3.3, 2.5, 3.9];

// 데이터 세트
const ageDatasets = {
    label: '나이 평균',
    data: avgByAgeData,
    fill: true,
    backgroundColor: 'rgba(255,228,0,0.2)',
    borderColor: '#FFE400',
    borderWidth: 1
};

const positionDatasets = {
    label: '포지션별 평균',
    data: avgByPositionData,
    fill: true,
    backgroundColor: 'rgba(255,228,0,0.2)',
    borderColor: '#FFE400',
    borderWidth: 1
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

// 평균 보기 - 나이별 평균
const data_byAge = {
    labels: typeLabels,
    datasets: [ageDatasets]
};

// 평균 보기 - 포지션별 평균
const data_byPosition = {
    labels: typeLabels,
    datasets: [positionDatasets]
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

// 레이더 차트
const config_radar = {
    type: 'radar',
    data: data_byAge,
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

// 첫 화면: 평균 보기 - 내 평균
const avgChart = new Chart(radarChart, config_radar);
const dateChart = new Chart(lineChart, config_line);

// 필터
const averageFilter = document.getElementById("average-filter");
const dateFilter = document.getElementById("date-filter");

function avgFilterChange() {
    var selected = averageFilter.options[averageFilter.selectedIndex].value;
    if (selected === "byAge") config_radar.data = data_byAge;
    if (selected === "byPosition") config_radar.data = data_byPosition;
    avgChart.update();
}

function dateFilterChange() {
    var selected = dateFilter.options[dateFilter.selectedIndex].value;
    if (selected === "byMonth") config_line.data = data_byMonth;
    if (selected === "byWeek") config_line.data = data_byDay;
    dateChart.update();
}