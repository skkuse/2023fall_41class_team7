import "./index.css";
import "./Server.css";
import { Accordion } from "flowbite-react";

function Server() {
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
                Type of Core
              </dt>

              <dd class="text-4xl font-extrabold text-blue-600 md:text-5xl">
                CPU
              </dd>
            </div>

            <div class="flex flex-col px-4 py-8 text-center">
              <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                Number of Core
              </dt>

              <dd class="text-4xl font-extrabold text-blue-600 md:text-5xl">
                12
              </dd>
            </div>

            <div class="flex flex-col px-4 py-8 text-center">
              <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                Model
              </dt>

              <dd class="text-2xl font-extrabold text-blue-600 md:text-3xl">
                Xeon-E5-2683 v4
              </dd>
            </div>
          </dl>
        </div>
        <div class="mt-8 sm:mt-12">
          <dl class="grid grid-cols-1 gap-4 sm:grid-cols-3 sm:divide-x sm:divide-gray-100">
            <div class="flex flex-col px-4 py-8 text-center">
              <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                Memory
              </dt>

              <dd class="text-4xl font-extrabold text-blue-600 md:text-5xl">
                64GB
              </dd>
            </div>

            <div class="flex flex-col px-4 py-8 text-center">
              <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                Platform
              </dt>

              <dd class="text-4xl font-extrabold text-blue-600 md:text-5xl">
                local
              </dd>
            </div>

            <div class="flex flex-col px-4 py-8 text-center">
              <dt class="mt-5 order-last text-lg font-medium text-gray-500">
                Location
              </dt>

              <dd class="text-4xl font-extrabold text-blue-600 md:text-5xl">
                Korea
              </dd>
            </div>
          </dl>
        </div>
      </div>
      <div class="mt-10 p-10">
        {/* <div
          role="status"
          class="max-w-full p-4 space-y-4 border border-gray-200 divide-y divide-gray-200 rounded shadow animate-pulse dark:divide-gray-700 md:p-6 dark:border-gray-700"
        >
          <div class="flex items-center justify-between">
            <div>
              <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 mb-2.5">
                Types of Cores
              </div>
              <div class="w-32 h-2 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-700 w-12"></div>
          </div>
          <div class="flex items-center justify-between pt-4">
            <div>
              <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24 mb-2.5"></div>
              <div class="w-32 h-2 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-700 w-12"></div>
          </div>
          <div class="flex items-center justify-between pt-4">
            <div>
              <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24 mb-2.5"></div>
              <div class="w-32 h-2 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-700 w-12"></div>
          </div>
          <div class="flex items-center justify-between pt-4">
            <div>
              <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24 mb-2.5"></div>
              <div class="w-32 h-2 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-700 w-12"></div>
          </div>
          <div class="flex items-center justify-between pt-4">
            <div>
              <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24 mb-2.5"></div>
              <div class="w-32 h-2 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-700 w-12"></div>
          </div>
          <span class="sr-only">Loading...</span>
        </div> */}
        <Accordion collapseAll>
          <Accordion.Panel>
            <Accordion.Title>Types of Core</Accordion.Title>
            <Accordion.Content>
              {/* <p className="mb-2 text-gray-500 dark:text-gray-400">
                Flowbite is an open-source library of interactive components
                built on top of Tailwind CSS including buttons, dropdowns,
                modals, navbars, and more.
              </p>
              <p className="text-gray-500 dark:text-gray-400">
                Check out this guide to learn how to&nbsp;
                <a
                  href="https://flowbite.com/docs/getting-started/introduction/"
                  className="text-cyan-600 hover:underline dark:text-cyan-500"
                >
                  get started&nbsp;
                </a>
                and start developing websites even faster with components on top
                of Tailwind CSS.
              </p> */}
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg bg-gray-200 lg:col-span-2"></div>
                <div class="h-32 rounded-lg flex justify-center items-center">
                  <select
                    name="HeadlineAct"
                    id="HeadlineAct"
                    class="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                  >
                    <option value="">CPU</option>
                    <option value="JM">GPU</option>
                    <option value="SRV">Both</option>
                  </select>
                </div>
              </div>

              {/* <div>
                <label
                  for="HeadlineAct"
                  class="block text-sm font-medium text-gray-900"
                >
                  Headliner
                </label>
              </div> */}
            </Accordion.Content>
          </Accordion.Panel>
          <Accordion.Panel>
            <Accordion.Title>Number of Core</Accordion.Title>
            <Accordion.Content>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg bg-gray-200 lg:col-span-2"></div>
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
                      >
                        &minus;
                      </button>

                      <input
                        type="number"
                        id="Quantity"
                        value="1"
                        class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                      />

                      <button
                        type="button"
                        class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
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
            <Accordion.Title>Model</Accordion.Title>
            <Accordion.Content>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg bg-gray-200 lg:col-span-2"></div>
                <div class="h-32 rounded-lg flex justify-center items-center">
                  <select
                    name="HeadlineAct"
                    id="HeadlineAct"
                    class="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                  >
                    <option value="">Xeon E5-2683 v4</option>
                    <option value="JM">Ryzen 9 3950X</option>
                    <option value="SRV">Other</option>
                  </select>
                </div>
              </div>
            </Accordion.Content>
          </Accordion.Panel>
          <Accordion.Panel>
            <Accordion.Title>Memory</Accordion.Title>
            <Accordion.Content>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg bg-gray-200 lg:col-span-2"></div>
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
                      >
                        &minus;
                      </button>

                      <input
                        type="number"
                        id="Quantity"
                        value="64"
                        class="h-10 w-24 rounded border-gray-200 sm:text-sm"
                      />

                      <button
                        type="button"
                        class="h-10 w-10 leading-10 text-gray-600 transition hover:opacity-75"
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
            <Accordion.Title>Platform</Accordion.Title>
            <Accordion.Content>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg bg-gray-200 lg:col-span-2"></div>
                <div class="h-32 rounded-lg flex justify-center items-center">
                  <select
                    name="HeadlineAct"
                    id="HeadlineAct"
                    class="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                  >
                    <option value="">Local server</option>
                    <option value="JM">Personal computer</option>
                    <option value="SRV">Cloud computing</option>
                  </select>
                </div>
              </div>
            </Accordion.Content>
          </Accordion.Panel>
          <Accordion.Panel>
            <Accordion.Title>Location</Accordion.Title>
            <Accordion.Content>
              <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-8">
                <div class="h-32 rounded-lg bg-gray-200 lg:col-span-2"></div>
                <div class="h-32 rounded-lg flex justify-center items-center">
                  <select
                    name="HeadlineAct"
                    id="HeadlineAct"
                    class="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                  >
                    <option value="">Asia</option>
                    <option value="JM">Europe</option>
                    <option value="SRV">Other</option>
                  </select>
                </div>
              </div>
            </Accordion.Content>
          </Accordion.Panel>
        </Accordion>
      </div>
    </div>
  );
}

export default Server;
