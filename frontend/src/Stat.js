import "./index.css";
import "./stats.css";
import car from './assets/car.png';
import plane from './assets/plane.png';
import ddogas from './assets/ddogas.png';
import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';

const stat_components = { //각 stats의 숫자
  carbon: null,
  car: null,
  plane: null,
  energy_needed:null,
  tree:null
}; 
const hw_components = { //각 hw의 비율. 없을시 null return
  cpu_rate: 1.7,
  gpu_rate: 1.25,
  memory_rate: 1
}; 
const region_components = { 
  cur:2, aus:1.9, ind:1.8, chi:1.7, usa:1.6, 
  eng:1.5, can:1.4, fra:1.3, swi:1.2, swe:1.1
}; 

function setComponents() {
  //json parsing
}

function showStats(){
  return( 
  <div class="flex flex-col m-10 ">
    <div class=" mb-10">
      <h1 class="mb-4 text-3xl font-extrabold text-gray-900 dark:text-white md:text-5xl lg:text-6xl">
        <span class="text-transparent bg-clip-text bg-gradient-to-r to-emerald-600 from-sky-400">
          Statistics
        </span>{" "}
        Overview 
      </h1>
    </div>

    <div class="w-full h-60 bg-stat-gray flex items-center ">
      <img src={ddogas} alt="Your Image" class="mr-2 w-190 h-48 pl-8"/>
      <div class="flex-grow bg-stat-gray h-16"></div> 
      <div class="flex items-center flex flex-col w-96">
        <div class="font-bold text-4xl text-black py-2">{stat_components.carbon} g CO2e</div>
        <div class="italic text-2xl text-black">carbon footprint</div>
      </div>
    </div>

    <div class="w-full h-60 bg-stat-lemon flex items-center ">
      <img src={ddogas} alt="Your Image" class="mr-2 w-190 h-48 pl-8"/>
      <div class="flex-grow bg-stat-lemon h-16"></div> 
      <div class="flex items-center flex flex-col w-96">
        <div class="font-bold text-4xl text-black py-2">{stat_components.energy_needed}kWh</div>
        <div class="italic text-2xl text-black">energy needed</div>
      </div>
    </div>

    <div class="w-full h-60 bg-stat-orange flex items-center">
      <img src={car} alt="Your Image" class="mr-2 w-190 h-60"/>
      <div class="flex-grow bg-stat-orange h-16"></div> 
      <div class="flex items-center flex flex-col w-96">
        <div class="font-bold text-4xl text-black py-2">{stat_components.car}km</div>
        <div class="italic text-2xl text-black">in a passenger car</div>
      </div>
    </div>

    <div class="w-full h-60 bg-stat-skyblue flex items-center">
      <img src={plane} alt="Your Image" class="mr-2 w-190 h-48"/>
      <div class="flex-grow bg-stat-skyblue h-16"></div> 
      <div class="flex items-center flex flex-col w-96">
        <div class="font-bold text-4xl text-black py-2">{stat_components.plane}%</div>
        <div class="italic text-2xl text-black">flight from Incheon to London</div>
      </div>
    </div>

    <div class="w-full h-60 bg-stat-grass flex items-center">
      <img src={plane} alt="Your Image" class="mr-2 w-190 h-48"/>
      <div class="flex-grow bg-stat-grass h-16"></div> 
      <div class="flex items-center flex flex-col w-96">
        <div class="font-bold text-4xl text-black py-2">{stat_components.tree}km</div>
        <div class="italic text-2xl text-black">tree-months</div>
      </div>
    </div>
  </div>
  );
}

function ShowHWRate({ hw_components }) {
  const chartRef = useRef(null);

  useEffect(() => {
    if (chartRef.current) {
      const ctx = chartRef.current.getContext('2d');

      // 입력된 변수를 데이터로 변환
      const data = {
        labels: ['CPU', 'GPU', 'Memory'],
        datasets: [
          {
            data: [hw_components.cpu_rate, hw_components.gpu_rate, hw_components.memory_rate],
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
            hoverBackgroundColor: ['red', 'blue', 'yellow'],
          },
        ],
      };

      // 차트 생성 전에 기존 차트를 파기
      if (window.doughnut_chart !== undefined) {
        window.doughnut_chart.destroy();
      }

      // 차트 생성
      window.doughnut_chart = new Chart(ctx, {
        type: 'doughnut',
        data: data,
      });
    }
  }, [hw_components.cpu_rate, hw_components.gpu_rate, hw_components.memory_rate]);

  return(
    <div>
      <canvas ref={chartRef} width={300} height={300}/>
    </div>
  ); 
}



function ShowRegionGraph({ region_components }) {
  const chartRef = useRef(null);

  useEffect(() => {
    if (chartRef.current) {
      const ctx = chartRef.current.getContext('2d');

      // 입력된 변수를 데이터로 변환
      const data = {
        labels: ['Cur', 'Aus', 'Ind', 'Chi', 'USA', 'Eng', 'Can', 'Fra', 'Swi', 'Swe'],
        datasets: [
          {
            label: 'Carbon Footprint',
            data: [
              region_components.cur, region_components.aus, region_components.ind,
              region_components.chi, region_components.usa, region_components.eng,
              region_components.can, region_components.fra, region_components.swi,
              region_components.swe
            ],
            backgroundColor: [
              'gold', 'green', 'purple', 'coral', 'pink',
              '#FF6384', '#36A2EB', '#FFCE56', 'red', 'blue'
              
            ],
            hoverBackgroundColor: [
              'yellow', 'lightgreen', 'mediumorchid', 'orange', 'lightpink',
              'lightcoral', 'lightskyblue', 'lightgoldenrodyellow', 'salmon', 'deepskyblue'
            ],
            borderWidth: 1,
          },
        ],
      };

      // 차트 생성 전에 기존 차트를 파기
      if (window.bar_chart !== undefined) {
        window.bar_chart.destroy();
      }

      // 차트 생성
      window.bar_chart = new Chart(ctx, {
        type: 'bar', // 막대 그래프로 변경
        data: data,
        options: {
          scales: {
            y: {
              beginAtZero: true // y 축이 0에서 시작하도록 설정
            }
          }
        }
      });
    }
  }, [
    region_components.cur, region_components.aus, region_components.ind,
    region_components.chi, region_components.usa, region_components.eng,
    region_components.can, region_components.fra, region_components.swi,
    region_components.swe
  ]);

  return(
    <div>
      <canvas ref={chartRef} width={300} height={300} />
    </div>
  ); 
}

function Stat() {
  setComponents()
  return (
    <div class="flex flex-col stats_area m-10 ">
      {showStats()}
      <div class='flex w-full'>
        <div class='flex-grow'>
          <ShowHWRate hw_components={hw_components} />
        </div>
        <div class='flex-grow'>
          <ShowRegionGraph region_components={region_components} />
        </div>
      </div>
    </div>
  
  );
}

export default Stat;