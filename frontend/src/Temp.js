import "./index.css";
import "./Server.css";
import React, { useContext, useState, useEffect } from "react";
import { Accordion } from "flowbite-react";
import { HWContext, HWProvider } from "./Hardware";

function InnerComponent() {
  const { HWValue, setHWValue } = useContext(HWContext);
  const location = HWValue.location;
  const psf = HWValue.psf;
  const pue = HWValue.pue;
  const memory = HWValue.memory;
  const type = HWValue.type;
  const cpu = HWValue.cpu;
  const gpu = HWValue.gpu;

  const continents = {
    Africa: ["Gambia, The", "South Africa"],
    Asia: [
      "Thailand",
      "Turkey",
      "Korea",
      "India",
      "Indonesia",
      "United Arab Emirates",
      "China",
      "Saudi Arabia",
      "Israel",
      "Japan",
      "Singapore",
    ],
    Europe: [
      "Lithuania",
      "Netherlands",
      "Italy",
      "Slovenia",
      "Belgium",
      "Portugal",
      "Spain",
      "Germany",
      "Norway",
      "Bulgaria",
      "Czech Republic",
      "Ireland",
      "Malta",
      "Switzerland",
      "Sweden",
      "Luxembourg",
      "Estonia",
      "Russian Federation",
      "Cyprus",
      "Latvia",
      "Romania",
      "United Kingdom",
      "Serbia",
      "Denmark",
      "Austria",
      "Croatia",
      "Poland",
      "Hungary",
      "France",
      "Iceland",
      "Finland",
      "Greece",
      "Slovakia",
    ],
    NorthAmerica: ["Mexico", "United States", "Canada"],
    Oceania: ["New Zealand", "Australia"],
    SouthAmerica: ["Brazil", "Argentina"],
  };

  const cpu_models = [
    "A8-7680",
    "A9-9425 SoC",
    "AMD 7552",
    "AMD EPYC 7251",
    "Any",
    "Athlon 3000G",
    "Core 2 Quad Q6600",
    "Core i3-10100",
    "Core i3-10300",
    "Core i3-10320",
    "Core i3-10350K",
    "Core i3-9100",
    "Core i3-9100F",
    "Core i5-10400",
    "Core i5-10400F",
    "Core i5-10500",
    "Core i5-10600",
    "Core i5-10600K",
    "Core i5-3570K",
    "Core i5-4460",
    "Core i5-9400",
    "Core i5-9400F",
    "Core i5-9600KF",
    "Core i7-10700",
    "Core i7-10700K",
    "Core i7-4930K",
    "Core i7-6700K",
    "Core i7-8700K",
    "Core i7-9700F",
    "Core i7-9700K",
    "Core i9-10900K",
    "Core i9-10900KF",
    "Core i9-10900XE",
    "Core i9-10920XE",
    "Core i9-9900K",
    "FX-6300",
    "FX-8350",
    "Ryzen 3 2200G",
    "Ryzen 3 3200G",
    "Ryzen 3 3200U",
    "Ryzen 5 1600",
    "Ryzen 5 2600",
    "Ryzen 5 3400G",
    "Ryzen 5 3500U",
    "Ryzen 5 3600",
    "Ryzen 5 3600X",
    "Ryzen 7 2700X",
    "Ryzen 7 3700X",
    "Ryzen 7 3800X",
    "Ryzen 9 3900X",
    "Ryzen 9 3950X",
    "Ryzen Threadripper 2990WX",
    "Ryzen Threadripper 3990X",
    "Xeon E5-2660 v3",
    "Xeon E5-2665",
    "Xeon E5-2670",
    "Xeon E5-2670 v2",
    "Xeon E5-2680 v3",
    "Xeon E5-2683 v4",
    "Xeon E5-2690 v2",
    "Xeon E5-2690 v3",
    "Xeon E5-2695 v4",
    "Xeon E5-2697 v4",
    "Xeon E5-2699 v3",
    "Xeon E5-2699 v4",
    "Xeon E5-4610 v4",
    "Xeon E5-4620",
    "Xeon E5-4650L",
    "Xeon E7-8867 v3",
    "Xeon E7-8880 v4",
    "Xeon Gold 6142",
    "Xeon Gold 6148",
    "Xeon Gold 6248",
    "Xeon Gold 6252",
    "Xeon L5640",
    "Xeon Phi 5110P",
    "Xeon Platinum 9282",
    "Xeon X3430",
    "Xeon X5660",
  ];

  const gpu_models = [
    "NVIDIA Jetson AGX Xavier",
    "NVIDIA Tesla T4",
    "AMD RX480",
    "NVIDIA GTX 1080",
    "TPU v3",
    "Any",
    "NVIDIA RTX 2080",
    "NVIDIA RTX 2080 Ti",
    "NVIDIA GTX 1080 Ti",
    "NVIDIA Titan V",
    "TPU v2",
    "NVIDIA GTX TITAN X",
    "NVIDIA TITAN X Pascal",
    "NVIDIA Tesla P100 PCIe",
    "NVIDIA Tesla V100",
    "TPU v3 pod",
    "NVIDIA A100 PCIe",
    "NVIDIA Tesla P4",
    "NVIDIA Tesla K80",
  ];
  const regions_lst = ["aa", "bb", "cc"];

  const [oneContinent, setOneContinent] = useState("Asia"); //country 리스트 정의를 위한 하나의 continent select
  const [countries, setCountries] = useState(continents[oneContinent]); //country 리스트 정의
  const [oneCountry, setOneCountry] = useState("Korea"); //region 리스트 정의를 위한 하나의 country select
  const [regions, setRegions] = useState(regions_lst); //region 리스트 정의

  const changeContinent = (event) => {
    const chosenContinent = event.target.value;
    setOneContinent(chosenContinent); //select 대륙 바꾸기
    setCountries(continents[chosenContinent] || []); //country 리스트 바꾸기
  };

  const changeCountry = (event) => {
    const chosenCountry = event.target.value;
    setOneCountry(chosenCountry); //select 국가 바꾸기
    //setRegions(countries[chosenCountry] || []); -> region도 바꿔야 되는데 일단 이건 api 적용할 때 바꾸자,,
  };

  const changeRegion = (event) => {
    //region이 변경될 시에만 location 바꾸기..
    changeLocation(oneContinent, oneCountry, event.target.value);
  };

  const changeLocation = (value1, value2, value3) => {
    setHWValue({
      ...HWValue,
      location: { continent: value1, country: value2, region: value3 },
    });
  };

  const changePsf = (value) => {
    setHWValue({ ...HWValue, psf: value });
  };

  const changePue = (value) => {
    setHWValue({ ...HWValue, pue: value });
  };

  const changeMemory = (value) => {
    setHWValue({ ...HWValue, memory: value });
  };

  const changeType = (event) => {
    setHWValue({ ...HWValue, type: event.target.value });
  };

  const changeCPUCore = (value) => {
    changeCpu(value, cpu.model, cpu.usage, cpu.tdp);
  };

  const changeCPUModel = (event) => {
    changeCpu(cpu.core, event.target.value, cpu.usage, cpu.tdp);
  };

  const changeCPUUsage = (value) => {
    changeCpu(cpu.core, cpu.model, value, cpu.tdp);
  };

  const changeCPUTdp = (value) => {
    changeCpu(cpu.core, cpu.model, cpu.usage, value);
  };

  const changeGPUCore = (value) => {
    changeGpu(value, gpu.model, gpu.usage, gpu.tdp);
  };

  const changeGPUModel = (event) => {
    changeGpu(gpu.core, event.target.value, gpu.usage, gpu.tdp);
  };

  const changeGPUUsage = (value) => {
    changeGpu(gpu.core, gpu.model, value, gpu.tdp);
  };

  const changeGPUTdp = (value) => {
    changeGpu(gpu.core, gpu.model, gpu.usage, value);
  };

  const changeCpu = (value1, value2, value3, value4) => {
    setHWValue({
      ...HWValue,
      cpu: { core: value1, model: value2, usage: value3, tdp: value4 },
    });
  };

  const changeGpu = (value1, value2, value3) => {
    setHWValue({
      ...HWValue,
      gpu: { core: value1, model: value2, usage: value3 },
    });
  };

  return (
    <div class="mt-10 server_area">
      <div class="ml-10">
        <h1 class="mb-4 text-3xl font-extrabold text-gray-900 dark:text-white md:text-5xl lg:text-6xl">
          <span class="text-transparent bg-clip-text bg-gradient-to-r to-emerald-600 from-sky-400">
            Server
          </span>{" "}
          Setting
        </h1>
        <p class="mt-10 text-lg font-normal text-gray-500 lg:text-xl dark:text-gray-400">
          Check the server information. And check the changing information by
          changing it to the desired figure.
        </p>
      </div>
      <div>
        <div class="mt-8 sm:mt-12">
          <dl class="grid grid-cols-1 gap-4 sm:grid-cols-3 sm:divide-x sm:divide-gray-100">
            <div class="flex flex-col px-4 py-8 text-center">
              <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                Memory
              </dt>

              <dd class="text-4xl font-extrabold text-blue-600 md:text-5xl">
                {memory}
              </dd>
            </div>

            <div class="flex flex-col px-4 py-8 text-center">
              <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                PSF
              </dt>

              <dd class="text-4xl font-extrabold text-blue-600 md:text-5xl">
                {psf}
              </dd>
            </div>

            <div class="flex flex-col px-4 py-8 text-center">
              <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                PUE
              </dt>

              <dd class="text-4xl font-extrabold text-blue-600 md:text-5xl">
                {pue}
              </dd>
            </div>
          </dl>
        </div>
        <div class="mt-8 sm:mt-12">
          <dl class="grid grid-cols-1 gap-4 sm:grid-cols-3 sm:divide-x sm:divide-gray-100">
            {(type === "cpu" || type === "both") && (
              <div class="flex flex-col px-4 py-8 text-center">
                <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                  CPU
                </dt>

                <dd class="text-xl font-extrabold text-blue-600 md:text-2xl">
                  Core : {cpu.core} <br />
                  Model : {cpu.model} <br />
                  Usage : {cpu.usage} <br />
                </dd>
              </div>
            )}
            {(type === "gpu" || type === "both") && (
              <div class="flex flex-col px-4 py-8 text-center">
                <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                  GPU
                </dt>

                <dd class="text-xl font-extrabold text-blue-600 md:text-2xl">
                  Core : {gpu.core} <br />
                  Model : {gpu.model} <br />
                  Usage : {gpu.usage} <br />
                </dd>
              </div>
            )}

            <div class="flex flex-col px-4 py-8 text-center">
              <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                Location
              </dt>

              <dd class="text-2xl font-extrabold text-blue-600 md:text-3xl">
                {location.continent} <br />
                {location.country}
              </dd>
            </div>
          </dl>
        </div>
      </div>
      <div class="mt-10 p-10">
        <Accordion collapseAll>
          <Accordion.Panel>
            <Accordion.Title>Types of Core</Accordion.Title>
            <Accordion.Content>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg flex text-center items-center justify-center font-extrabold text-gray-600 text-2xl lg:col-span-2">
                  Type of Core
                </div>
                <div class="h-32 rounded-lg flex justify-center items-center">
                  <select
                    name="HeadlineAct"
                    id="HeadlineAct"
                    class="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                    onChange={changeType}
                  >
                    <option value="cpu">CPU</option>
                    <option value="gpu">GPU</option>
                    <option value="both">Both</option>
                  </select>
                </div>
              </div>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-2 lg:gap-8 m-10">
                {(type === "cpu" || type === "both") && (
                  <div class="w-full max-w-md p-4 bg-white border border-gray-200 rounded-lg shadow sm:p-8 dark:bg-gray-800 dark:border-gray-700">
                    <div class="flex items-center justify-between mb-4">
                      <h5 class="text-xl font-bold leading-none text-gray-900 dark:text-white">
                        CPU
                      </h5>
                    </div>
                    <div class="flow-root">
                      <ul
                        role="list"
                        class="divide-y divide-gray-200 dark:divide-gray-700"
                      >
                        <li class="py-3 sm:py-4">
                          <div class="flex items-center">
                            <div class="flex-1 min-w-0 ms-4">
                              <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
                                Number of core
                              </p>
                            </div>
                            <div class="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                              <div class="flex items-center gap-1">
                                <button
                                  type="button"
                                  class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                                  onClick={() =>
                                    changeCPUCore(Math.max(0, cpu.core - 1))
                                  }
                                >
                                  &minus;
                                </button>

                                <input
                                  type="number"
                                  id="memory"
                                  value={cpu.core}
                                  class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                                  onChange={(e) =>
                                    changeCPUCore(
                                      Math.max(0, parseInt(e.target.value))
                                    )
                                  }
                                />

                                <button
                                  type="button"
                                  class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                                  onClick={() => changeCPUCore(cpu.core + 1)}
                                >
                                  +
                                </button>
                              </div>
                            </div>
                          </div>
                        </li>
                        <li class="py-3 sm:py-4">
                          <div class="flex items-center ">
                            <div class="flex-1 min-w-0 ms-4">
                              <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
                                Usage
                              </p>
                            </div>
                            <div class="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                              <button
                                type="button"
                                class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                                onClick={() =>
                                  changeCPUUsage(
                                    Math.max(
                                      0,
                                      parseFloat((cpu.usage - 0.1).toFixed(1))
                                    )
                                  )
                                }
                              >
                                &minus;
                              </button>
                              <input
                                type="number"
                                id="pue"
                                value={cpu.usage}
                                class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                                onChange={(e) =>
                                  changeCPUUsage(
                                    Math.max(0, parseFloat(e.target.value))
                                  )
                                }
                                step="0.1"
                              />
                              <button
                                type="button"
                                class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                                onClick={() =>
                                  changeCPUUsage(
                                    parseFloat((cpu.usage + 0.1).toFixed(1))
                                  )
                                }
                              >
                                +
                              </button>
                            </div>
                          </div>
                        </li>
                        <li class="py-3 sm:py-4">
                          <div class="flex items-center">
                            <div class="flex-1 min-w-0 ms-4">
                              <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
                                Model
                              </p>
                            </div>
                            <div class="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                              <select
                                name="cpu_model"
                                id="cpu_model"
                                className="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                                onChange={changeCPUModel}
                              >
                                {cpu_models.map((model, index) => (
                                  <option key={index} value={model}>
                                    {model}
                                  </option>
                                ))}
                              </select>
                            </div>
                          </div>
                        </li>
                        {cpu.model === "Any" && (
                          <li class="py-3 sm:py-4">
                            <div class="flex items-center ">
                              <div class="flex-1 min-w-0 ms-4">
                                <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
                                  Tdp
                                </p>
                              </div>
                              <div class="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                                <input
                                  type="number"
                                  id="tdp"
                                  value={cpu.tdp}
                                  class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                                  onChange={(e) =>
                                    changeCPUTdp(
                                      Math.max(0, parseFloat(e.target.value))
                                    )
                                  }
                                  step="0.1"
                                />
                              </div>
                            </div>
                          </li>
                        )}
                      </ul>
                    </div>
                  </div>
                )}

                {(type === "gpu" || type === "both") && (
                  <div class="w-full max-w-md p-4 bg-white border border-gray-200 rounded-lg shadow sm:p-8 dark:bg-gray-800 dark:border-gray-700">
                    <div class="flex items-center justify-between mb-4">
                      <h5 class="text-xl font-bold leading-none text-gray-900 dark:text-white">
                        GPU
                      </h5>
                    </div>
                    <div class="flow-root">
                      <ul
                        role="list"
                        class="divide-y divide-gray-200 dark:divide-gray-700"
                      >
                        <li class="py-3 sm:py-4">
                          <div class="flex items-center">
                            <div class="flex-1 min-w-0 ms-4">
                              <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
                                Number of core
                              </p>
                            </div>
                            <div class="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                              <div class="flex items-center gap-1">
                                <button
                                  type="button"
                                  class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                                  onClick={() =>
                                    changeGPUCore(Math.max(0, gpu.core - 1))
                                  }
                                >
                                  &minus;
                                </button>

                                <input
                                  type="number"
                                  id="memory"
                                  value={gpu.core}
                                  class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                                  onChange={(e) =>
                                    changeGPUCore(
                                      Math.max(0, parseInt(e.target.value))
                                    )
                                  }
                                />

                                <button
                                  type="button"
                                  class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                                  onClick={() => changeGPUCore(gpu.core + 1)}
                                >
                                  +
                                </button>
                              </div>
                            </div>
                          </div>
                        </li>
                        <li class="py-3 sm:py-4">
                          <div class="flex items-center ">
                            <div class="flex-1 min-w-0 ms-4">
                              <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
                                Usage
                              </p>
                            </div>
                            <div class="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                              <button
                                type="button"
                                class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                                onClick={() =>
                                  changeGPUUsage(
                                    Math.max(
                                      0,
                                      parseFloat((gpu.usage - 0.1).toFixed(1))
                                    )
                                  )
                                }
                              >
                                &minus;
                              </button>
                              <input
                                type="number"
                                id="cpu usage"
                                value={cpu.usage}
                                class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                                onChange={(e) =>
                                  changeGPUUsage(
                                    Math.max(0, parseFloat(e.target.value))
                                  )
                                }
                                step="0.1"
                              />
                              <button
                                type="button"
                                class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                                onClick={() =>
                                  changeGPUUsage(
                                    parseFloat((gpu.usage + 0.1).toFixed(1))
                                  )
                                }
                              >
                                +
                              </button>
                            </div>
                          </div>
                        </li>
                        <li class="py-3 sm:py-4">
                          <div class="flex items-center">
                            <div class="flex-1 min-w-0 ms-4">
                              <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
                                Model
                              </p>
                            </div>
                            <div class="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                              <select
                                name="gpu_model"
                                id="gpu_model"
                                className="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                                onChange={changeGPUModel}
                              >
                                {gpu_models.map((model, index) => (
                                  <option key={index} value={model}>
                                    {model}
                                  </option>
                                ))}
                              </select>
                            </div>
                          </div>
                        </li>
                        {gpu.model === "Any" && (
                          <li class="py-3 sm:py-4">
                            <div class="flex items-center ">
                              <div class="flex-1 min-w-0 ms-4">
                                <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
                                  Tdp
                                </p>
                              </div>
                              <div class="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                                <input
                                  type="number"
                                  id="tdp"
                                  value={gpu.tdp}
                                  class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                                  onChange={(e) =>
                                    changeGPUTdp(
                                      Math.max(0, parseFloat(e.target.value))
                                    )
                                  }
                                  step="0.1"
                                />
                              </div>
                            </div>
                          </li>
                        )}
                      </ul>
                    </div>
                  </div>
                )}
              </div>
            </Accordion.Content>
          </Accordion.Panel>

          <Accordion.Panel>
            <Accordion.Title>Memory</Accordion.Title>
            <Accordion.Content>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg lg:col-span-2 flex text-center items-center justify-center font-extrabold text-gray-600 text-2xl">
                  Memory
                </div>
                <div class="h-32 rounded-lg flex justify-center items-center">
                  <div>
                    <label for="Quantity" class="sr-only">
                      {" "}
                      Quantity{" "}
                    </label>

                    <div class="flex items-center gap-1">
                      <button
                        type="button"
                        class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                        onClick={() => changeMemory(Math.max(0, memory - 1))}
                      >
                        &minus;
                      </button>

                      <input
                        type="number"
                        id="memory"
                        value={memory}
                        class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                        onChange={(e) =>
                          changeMemory(Math.max(0, parseInt(e.target.value)))
                        }
                      />

                      <button
                        type="button"
                        class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                        onClick={() => changeMemory(memory + 1)}
                      >
                        +
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </Accordion.Content>
          </Accordion.Panel>
          <Accordion.Panel>
            <Accordion.Title>PUE</Accordion.Title>
            <Accordion.Content>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg flex text-center items-center justify-center font-extrabold text-gray-600 text-2xl lg:col-span-2">
                  PUE
                </div>
                <div class="h-32 rounded-lg flex justify-center items-center">
                  <button
                    type="button"
                    class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                    onClick={() =>
                      changePue(Math.max(0, parseFloat((pue - 0.1).toFixed(1))))
                    }
                  >
                    &minus;
                  </button>
                  <input
                    type="number"
                    id="pue"
                    value={pue}
                    class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                    onChange={(e) =>
                      changePue(Math.max(0, parseFloat(e.target.value)))
                    }
                    step="0.1"
                    disabled
                  />
                  <button
                    type="button"
                    class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                    onClick={() =>
                      changePue(parseFloat((pue + 0.1).toFixed(1)))
                    }
                  >
                    +
                  </button>
                </div>
              </div>
            </Accordion.Content>
          </Accordion.Panel>
          <Accordion.Panel>
            <Accordion.Title>PSF</Accordion.Title>
            <Accordion.Content>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg flex text-center items-center justify-center font-extrabold text-gray-600 text-2xl lg:col-span-2">
                  PSF
                </div>
                <div class="h-32 rounded-lg flex justify-center items-center">
                  <button
                    type="button"
                    class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                    onClick={() =>
                      changePsf(Math.max(0, parseFloat((psf - 0.1).toFixed(1))))
                    }
                  >
                    &minus;
                  </button>
                  <input
                    type="number"
                    id="psf"
                    value={psf}
                    class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                    onChange={(e) =>
                      changePsf(Math.max(0, parseFloat(e.target.value)))
                    }
                    step="0.1"
                  />
                  <button
                    type="button"
                    class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                    onClick={() =>
                      changePsf(parseFloat((psf + 0.1).toFixed(1)))
                    }
                  >
                    +
                  </button>
                </div>
              </div>
            </Accordion.Content>
          </Accordion.Panel>
          <Accordion.Panel>
            <Accordion.Title>Location</Accordion.Title>
            <Accordion.Content>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg flex text-center items-center justify-center font-extrabold text-gray-600 text-2xl lg:col-span-2">
                  Location
                </div>
                <div class="h-32 rounded-lg justify-center items-center">
                  <div class="grid grid-cols-2">
                    <div class="font-bold text-lg text-center flex items-center justify-center text-gray-600">
                      Continent
                    </div>
                    <select
                      name="HeadlineAct"
                      id="HeadlineAct"
                      class="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                      onChange={changeContinent}
                    >
                      <option value="Asia">Asia</option>
                      <option value="NorthAmerica">North America</option>
                      <option value="SouthAmerica">South America</option>
                      <option value="Europe">Europe</option>
                      <option value="Africa">Africa</option>
                      <option value="Oceania">Oceania</option>
                    </select>
                  </div>
                  <div class="grid grid-cols-2">
                    <div class="font-bold text-lg text-center flex items-center justify-center text-gray-600">
                      Country
                    </div>
                    <select
                      name="Country"
                      id="Country"
                      class="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                      onChange={changeCountry}
                    >
                      {countries.map((country, index) => (
                        <option key={index} value={country.toLowerCase()}>
                          {country}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div class="grid grid-cols-2">
                    <div class="font-bold text-lg text-center flex items-center justify-center text-gray-600">
                      Region
                    </div>
                    <select
                      name="Region"
                      id="Region"
                      class="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                      onChange={changeRegion}
                    >
                      {regions.map((region, index) => (
                        <option key={index} value={region.toLowerCase()}>
                          {region}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
              </div>
            </Accordion.Content>
          </Accordion.Panel>
        </Accordion>
      </div>
    </div>
  );
}
function Server() {
  return (
    <HWProvider>
      <InnerComponent />
    </HWProvider>
  );
}

export default Server;
