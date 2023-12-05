import "./index.css";
import "./Server.css";
import React, { useContext, useState, useEffect } from "react";
import { Accordion } from "flowbite-react";
import { HWContext } from "./Hardware";

function InnerComponent() {
  const { HWValue, setHWValue } = useContext(HWContext);
  // const hw = HWValue.HWSpecRequest;
  const cpu = HWValue.hwSpecRequest.cpuSpecRequest;
  const gpu = HWValue.hwSpecRequest.gpuSpecRequest;
  const memory = HWValue.hwSpecRequest.memoryGigaByte;
  const psf = HWValue.hwSpecRequest.psf;
  const location = HWValue.locationRequest;

  const [continents, setContinents] = useState(null);
  const [oneContinent, setOneContinent] = useState(location.continent); //country 리스트 정의를 위한 하나의 continent select
  const [countries, setCountries] = useState(null); //country 리스트 정의
  const [oneCountry, setOneCountry] = useState(location.country); //region 리스트 정의를 위한 하나의 country select
  const [regions, setRegions] = useState(null); //region 리스트 정의
  const [cpuModels, setCpuModels] = useState(null);
  const [gpuModels, setGpuModels] = useState(null);
  const [cpuExist, setCpuExist] = useState(true);
  const [gpuExist, setGpuExist] = useState(true);
  const [cpuHWValue, setcpuHWValue] = useState(true);
  const [gpuHWValue, setgpuHWValue] = useState(true);
  const [type, setType] = useState(null);

  useEffect(() => {
    if (cpu && gpu) {
      setType("both");
    } else if (cpu) {
      setType("cpu");
    } else {
      setType("gpu");
    }
  }, []);

  useEffect(() => {
    const showcpuModels = async () => {
      try {
        console.log("fetch...\n");
        const response = await fetch(
          "http://ec2-3-35-3-126.ap-northeast-2.compute.amazonaws.com:8080/model/cpu"
        );
        if (!response.ok) {
          throw new Error(`HTTP error, status : ${response.status}`);
        }
        const res = await response.json();
        const models_c = res.data || [];
        return models_c;
      } catch (error) {
        console.error("Fetching error: ", error);
      }
    };

    showcpuModels().then((models_c) => {
      if (Array.isArray(models_c)) {
        const updatedModels = [...models_c, "Direct Input"];
        setCpuModels(updatedModels);
      }
    });
  }, []);

  useEffect(() => {
    const showgpuModels = async () => {
      try {
        const response = await fetch(
          "http://ec2-3-35-3-126.ap-northeast-2.compute.amazonaws.com:8080/model/gpu"
        );
        if (!response.ok) {
          throw new Error(`HTTP error, status : ${response.status}`);
        }
        const res = await response.json();
        const models_g = res.data || [];
        return models_g;
      } catch (error) {
        console.error("Fetching error: ", error);
      }
    };

    showgpuModels().then((models_g) => {
      if (Array.isArray(models_g)) {
        const updatedModels = [...models_g, "Direct Input"];
        setGpuModels(updatedModels);
      }
    });
  }, []);

  useEffect(() => {
    const showContinent = async () => {
      try {
        const response = await fetch(
          "http://ec2-3-35-3-126.ap-northeast-2.compute.amazonaws.com:8080/location"
        );
        if (!response.ok) {
          throw new Error(`HTTP error, status : ${response.status}`);
        }
        const res = await response.json();
        const continents = res.data || [];
        return continents;
      } catch (error) {
        console.error("Fetching error: ", error);
      }
    };

    showContinent().then((continents) => setContinents(continents));
  }, []);

  useEffect(() => {
    const showCountry = async () => {
      try {
        const response = await fetch(
          `http://ec2-3-35-3-126.ap-northeast-2.compute.amazonaws.com:8080/location/${oneContinent}`
        );
        if (!response.ok) {
          throw new Error(`HTTP error, status : ${response.status}`);
        }
        const res = await response.json();
        const countries = res.data || [];
        return countries;
      } catch (error) {
        console.error("Fetching error: ", error);
      }
    };

    showCountry().then((countries) => setCountries(countries));
  }, []);

  useEffect(() => {
    const showCountry = async () => {
      try {
        const response = await fetch(
          `http://ec2-3-35-3-126.ap-northeast-2.compute.amazonaws.com:8080/location/${oneContinent}`
        );
        if (!response.ok) {
          throw new Error(`HTTP error, status : ${response.status}`);
        }
        const res = await response.json();
        const countries = res.data || [];
        return countries;
      } catch (error) {
        console.error("Fetching error: ", error);
      }
    };

    showCountry().then((countries) => {
      setCountries(countries);
      changeCountry({ target: { value: countries[0] } });
    });
  }, [oneContinent]);

  useEffect(() => {
    const showRegion = async () => {
      try {
        const response = await fetch(
          `http://ec2-3-35-3-126.ap-northeast-2.compute.amazonaws.com:8080/location/${oneContinent}/${oneCountry}`
        );
        if (!response.ok) {
          throw new Error(`HTTP error, status : ${response.status}`);
        }
        const res = await response.json();
        const regions = res.data || [];
        return regions;
      } catch (error) {
        console.error("Fetching error: ", error);
      }
    };

    showRegion().then((regions) => {
      setRegions(regions);
      changeRegion({ target: { value: regions[0] } });
    });
  }, [oneCountry]);

  useEffect(() => {
    if (cpuExist) {
      // console.log("add cpu request..");
      const cpuSpecRequest = {
        modelName: "Intel Core i9-14900K",
        usageFactor: 0.8,
        coreNumber: 8,
        tdp: null,
      };
      if (cpuHWValue === false) {
        setHWValue({
          ...HWValue,
          hwSpecRequest: {
            ...HWValue.hwSpecRequest,
            cpuSpecRequest: cpuSpecRequest,
          },
        });
        setcpuHWValue(true);
      }
      // console.log("change true...\n");

      //setcpuHWValue(true);
    } else if (!cpuExist) {
      if (cpuHWValue === true) {
        setHWValue({
          ...HWValue,
          hwSpecRequest: { ...HWValue.hwSpecRequest, cpuSpecRequest: null },
        });
      }
      setcpuHWValue(false);
    }
  }, [cpuExist]);

  useEffect(() => {
    if (gpuExist) {
      const gpuSpecRequest = {
        modelName: "WRadeon 520",
        usageFactor: 0.7,
        coreNumber: 7,
        tdp: null,
      };
      if (gpuHWValue === false) {
        setHWValue({
          ...HWValue,
          hwSpecRequest: {
            ...HWValue.hwSpecRequest,
            gpuSpecRequest: gpuSpecRequest,
          },
        });
        setgpuHWValue(true);
      }
      //setgpuHWValue(true);
    } else if (!gpuExist) {
      if (gpuHWValue === true) {
        setHWValue({
          ...HWValue,
          hwSpecRequest: { ...HWValue.hwSpecRequest, gpuSpecRequest: null },
        });
        setgpuHWValue(false);
      }
    }
  }, [gpuExist]);

  const changeContinent = (event) => {
    const chosenContinent = event.target.value;
    setOneContinent(chosenContinent); //select 대륙 바꾸기
  };

  const changeCountry = (event) => {
    const chosenCountry = event.target.value;
    setOneCountry(chosenCountry); //select 국가 바꾸기
    //   changeLocation(oneContinent, chosenCountry, "Any");
  };

  const changeRegion = (event) => {
    //region이 변경될 시에만 location 바꾸기..
    changeLocation(oneContinent, oneCountry, event.target.value);
  };

  const changeLocation = (value1, value2, value3) => {
    setHWValue({
      ...HWValue,
      locationRequest: { continent: value1, country: value2, region: value3 },
    });
  };

  const changePsf = (value) => {
    setHWValue({
      ...HWValue,
      hwSpecRequest: { ...HWValue.hwSpecRequest, psf: value },
    });
  };

  const changeMemory = (value) => {
    setHWValue({
      ...HWValue,
      hwSpecRequest: { ...HWValue.hwSpecRequest, memoryGigaByte: value },
    });
  };

  const changeType = (event) => {
    if (event.target.value === "cpu") {
      setCpuExist(true);
      setGpuExist(false);
      setType("cpu");
    } else if (event.target.value === "gpu") {
      setCpuExist(false);
      setGpuExist(true);
      setType("gpu");
    } else {
      setCpuExist(true);
      setGpuExist(true);
      setType("both");
    }
  };

  const changeCPUCore = (value) => {
    changeCpu(value, cpu.modelName, cpu.usageFactor, cpu.tdp);
  };

  const changeCPUModel = (event) => {
    if (event.target.value === "Direct Input") {
      changeCpu(cpu.coreNumber, event.target.value, cpu.usageFactor, cpu.tdp);
    } else {
      changeCpu(cpu.coreNumber, event.target.value, cpu.usageFactor, null);
    }
  };

  const changeCPUUsage = (value) => {
    changeCpu(cpu.coreNumber, cpu.modelName, value, cpu.tdp);
  };

  const changeCPUTdp = (value) => {
    changeCpu(cpu.coreNumber, cpu.modelName, cpu.usageFactor, value);
    //changeCpu(cpu.coreNumber, null, cpu.usageFactor, value);
  };

  const changeGPUCore = (value) => {
    changeGpu(value, gpu.modelName, gpu.usageFactor, gpu.tdp);
  };

  const changeGPUModel = (event) => {
    if (event.target.value === "Direct Input") {
      changeGpu(gpu.coreNumber, event.target.value, gpu.usageFactor, gpu.tdp);
    } else {
      changeGpu(gpu.coreNumber, event.target.value, gpu.usageFactor, null);
    }
  };

  const changeGPUUsage = (value) => {
    changeGpu(gpu.coreNumber, gpu.modelName, value, gpu.tdp);
  };

  const changeGPUTdp = (value) => {
    changeGpu(gpu.coreNumber, null, gpu.usageFactor, value);
  };

  const changeCpu = (value1, value2, value3, value4) => {
    setHWValue({
      ...HWValue,
      hwSpecRequest: {
        ...HWValue.hwSpecRequest,
        cpuSpecRequest: {
          coreNumber: value1,
          modelName: value2,
          usageFactor: value3,
          tdp: value4,
        },
      },
    });
  };

  const changeGpu = (value1, value2, value3, value4) => {
    setHWValue({
      ...HWValue,
      hwSpecRequest: {
        ...HWValue.hwSpecRequest,
        gpuSpecRequest: {
          coreNumber: value1,
          modelName: value2,
          usageFactor: value3,
          tdp: value4,
        },
      },
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
                1.0
              </dd>
            </div>
          </dl>
        </div>
        <div class="mt-8 sm:mt-12">
          <dl class="grid grid-cols-1 gap-4 sm:grid-cols-3 sm:divide-x sm:divide-gray-100">
            {cpuExist === true && cpu && (
              <div class="flex flex-col px-4 py-8 text-center">
                <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                  CPU
                </dt>

                <dd class="text-xl font-extrabold text-blue-600 md:text-2xl">
                  Core : {cpu.coreNumber} <br />
                  Model : {cpu.modelName} <br />
                  Usage : {cpu.usageFactor} <br />
                </dd>
              </div>
            )}
            {gpuExist === true && gpu && (
              <div class="flex flex-col px-4 py-8 text-center">
                <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                  GPU
                </dt>

                <dd class="text-xl font-extrabold text-blue-600 md:text-2xl">
                  Core : {gpu.coreNumber} <br />
                  Model : {gpu.modelName} <br />
                  Usage : {gpu.usageFactor} <br />
                </dd>
              </div>
            )}

            <div class="flex flex-col px-4 py-8 text-center">
              <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                Location
              </dt>

              <dd class="text-2xl font-extrabold text-blue-600 md:text-3xl">
                {location.continent} <br />
                {location.country} <br />
                {location.region}
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
                    value={type}
                  >
                    <option value="cpu">CPU</option>
                    <option value="gpu">GPU</option>
                    <option value="both">Both</option>
                  </select>
                </div>
              </div>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-2 lg:gap-8 m-10">
                {cpuExist === true && cpu && (
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
                                    changeCPUCore(
                                      Math.max(0, cpu.coreNumber - 1)
                                    )
                                  }
                                >
                                  &minus;
                                </button>

                                <input
                                  type="number"
                                  id="memory"
                                  value={cpu.coreNumber}
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
                                  onClick={() =>
                                    changeCPUCore(cpu.coreNumber + 1)
                                  }
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
                                      parseFloat(
                                        (cpu.usageFactor - 0.1).toFixed(1)
                                      )
                                    )
                                  )
                                }
                              >
                                &minus;
                              </button>
                              <input
                                type="number"
                                id="cpuUsage"
                                value={cpu.usageFactor}
                                class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                                onChange={(e) =>
                                  changeCPUUsage(
                                    Math.max(0, parseFloat(e.target.value))
                                  )
                                }
                                step="0.1"
                                max="1"
                              />
                              <button
                                type="button"
                                class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                                onClick={() => {
                                  const value = parseFloat(
                                    (cpu.usageFactor + 0.1).toFixed(1)
                                  );
                                  if (value <= 1) {
                                    changeCPUUsage(value);
                                  } //if
                                }}
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
                                value={cpu.modelName}
                              >
                                {Array.isArray(cpuModels) &&
                                  cpuModels.map((model, index) => (
                                    <option key={index} value={model}>
                                      {model}
                                    </option>
                                  ))}
                              </select>
                            </div>
                          </div>
                        </li>
                        {cpu.modelName === "Direct Input" && (
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

                {gpuExist === true && gpu && (
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
                                    changeGPUCore(
                                      Math.max(0, gpu.coreNumber - 1)
                                    )
                                  }
                                >
                                  &minus;
                                </button>

                                <input
                                  type="number"
                                  id="memory"
                                  value={gpu.coreNumber}
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
                                  onClick={() =>
                                    changeGPUCore(gpu.coreNumber + 1)
                                  }
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
                                      parseFloat(
                                        (gpu.usageFactor - 0.1).toFixed(1)
                                      )
                                    )
                                  )
                                }
                              >
                                &minus;
                              </button>
                              <input
                                type="number"
                                id="cpu usage"
                                value={gpu.usageFactor}
                                class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                                onChange={(e) =>
                                  changeGPUUsage(
                                    Math.max(0, parseFloat(e.target.value))
                                  )
                                }
                                step="0.1"
                                max="1"
                              />
                              <button
                                type="button"
                                class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                                onClick={() => {
                                  const value = parseFloat(
                                    (gpu.usageFactor + 0.1).toFixed(1)
                                  );
                                  if (value <= 1) {
                                    changeGPUUsage(value);
                                  }
                                }}
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
                                value={gpu.modelName}
                              >
                                {Array.isArray(gpuModels) &&
                                  gpuModels.map((model, index) => (
                                    <option key={index} value={model}>
                                      {model}
                                    </option>
                                  ))}
                              </select>
                            </div>
                          </div>
                        </li>
                        {gpu.modelName === "Direct Input" && (
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
                  {/* <button
                    type="button"
                    class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                    onClick={() =>
                      changePue(Math.max(0, parseFloat((pue - 0.1).toFixed(1))))
                    }
                  >
                    &minus;
                  </button> */}
                  <input
                    type="number"
                    id="pue"
                    value={1.0}
                    class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                    // onChange={(e) =>
                    //   changePue(Math.max(0, parseFloat(e.target.value)))
                    // }
                    step="0.1"
                    disabled
                  />
                  {/* <button
                    type="button"
                    class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
                    onClick={() =>
                      changePue(parseFloat((pue + 0.1).toFixed(1)))
                    }
                  >
                    +
                  </button> */}
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
                      value={oneContinent}
                    >
                      {Array.isArray(continents) &&
                        continents.map((continent, index) => (
                          <option key={index} value={continent}>
                            {continent}
                          </option>
                        ))}
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
                      value={oneCountry}
                    >
                      {Array.isArray(countries) &&
                        countries.map((country, index) => (
                          <option key={index} value={country}>
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
                      value={location.region}
                    >
                      {Array.isArray(regions) &&
                        regions.map((region, index) => (
                          <option key={index} value={region}>
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
  return <InnerComponent />;
}

export default Server;
