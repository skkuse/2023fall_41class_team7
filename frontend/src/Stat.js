import "./index.css";
import "./stats.css";
import car from './car.png';
import plane from './plane.png';
import ddogas from './ddogas.png';

const stat_components = { //각 stats의 숫자
  carbon: null,
  car: null,
  plane: null,
  energy_needed:null,
  tree:null
}; 
const hw_components = { //각 hw의 비율. 없을시 null return
  cpu_rate: null,
  gpu_rate: null,
  memory_rate: null
}; 
const region_components = { 
  cur:null, aus:null, ind:null, chi:null, usa:null, 
  eng:null, can:null, fra:null, swi:null, swe:null
}; 

function setStats() {
  //json parsing
}

function Stat() {

  setStats()
  return (
  
  <div class="flex flex-col stats_area m-10 ">
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
    <div class="flex items-center flex flex-col w-80">
      <div class="font-bold text-4xl text-black py-2">{stat_components.carbon} g CO2e</div>
      <div class="italic text-2xl text-black">carbon footprint</div>
    </div>
  </div>

  <div class="w-full h-60 bg-stat-orange flex items-center">
    <img src={car} alt="Your Image" class="mr-2 w-190 h-60"/>
    <div class="flex-grow bg-stat-orange h-16"></div> 
    <div class="flex items-center flex flex-col w-80">
      <div class="font-bold text-4xl text-black py-2">{stat_components.car}km</div>
      <div class="italic text-2xl text-black">in a passenger car</div>
    </div>
  </div>

  <div class="w-full h-60 bg-stat-skyblue flex items-center">
    <img src={plane} alt="Your Image" class="mr-2 w-190 h-48"/>
    <div class="flex-grow bg-stat-skyblue h-16"></div> 
    <div class="flex items-center flex flex-col w-80">
      <div class="font-bold text-4xl text-black py-2">{stat_components.plane}km</div>
      <div class="italic text-2xl text-black">of a flight Paris-London</div>
    </div>
  </div>

  <div class="w-full h-60 bg-stat-grass"></div>
</div>

);
}

export default Stat;
