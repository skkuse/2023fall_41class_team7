import "./index.css";
import car from './car.png';
import plane from './plane.png';
import ddogas from './ddogas.png';

function Stat() {

  return (<div class="flex flex-col">
  <div class="w-full h-60 bg-stat-gray flex items-center ">
    <img src={ddogas} alt="Your Image" class="mr-2 w-190 h-48 pl-8"/>
    <div class="flex-grow bg-stat-gray h-16"></div> 
    <div class="flex items-center flex flex-col w-80">
      <div class="font-bold text-4xl text-black py-2">250.64 g CO2e</div>
      <div class="italic text-2xl text-black">carbon footprint</div>
    </div>
  </div>

  <div class="w-full h-60 bg-stat-orange flex items-center">
    <img src={car} alt="Your Image" class="mr-2 w-190 h-60"/>
    <div class="flex-grow bg-stat-orange h-16"></div> 
    <div class="flex items-center flex flex-col w-80">
      <div class="font-bold text-4xl text-black py-2">1km</div>
      <div class="italic text-2xl text-black">in a passenger car</div>
    </div>
  </div>

  <div class="w-full h-60 bg-stat-skyblue flex items-center">
    <img src={plane} alt="Your Image" class="mr-2 w-190 h-48"/>
    <div class="flex-grow bg-stat-skyblue h-16"></div> 
    <div class="flex items-center flex-col w-80">
      <div class="font-bold text-4xl text-black py-2">1km</div>
      <div class="italic text-2xl text-black">of a flight Paris-London</div>
    </div>
  </div>

  <div class="w-full h-60 bg-stat-grass"></div>
</div>

);
}

export default Stat;
