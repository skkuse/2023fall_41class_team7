import "./index.css";
import { Sidebar } from "flowbite-react";
import { HiChartPie, HiInbox, HiViewBoards } from "react-icons/hi";
import { BrowserRouter as Router, Link, Route, Routes } from "react-router-dom";
import Code from "./Code";
import Stat from "./Stat";
import Server from "./Server";
import Info from "./Info";
import { HWProvider } from "./Hardware";
import { CarbonProvider } from "./Carbon";

function Nav() {
  return (
    <div>
      <div class="fixed min-h-screen h-full px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800 border-r">
        <Sidebar aria-label="Sidebar with logo branding example">
          <Sidebar.Logo img="/eco22.png" imgAlt="Flowbite logo">
            <Link to="/">ECO2</Link>
          </Sidebar.Logo>
          <Sidebar.Items>
            <Sidebar.ItemGroup>
              <Sidebar.Item icon={HiInbox}>
                <Link to="/">Home</Link>
              </Sidebar.Item>
              <Sidebar.Item icon={HiViewBoards}>
                <Link to="/server">Server</Link>
              </Sidebar.Item>
              <Sidebar.Item icon={HiChartPie}>
                <Link to="/info">Info</Link>
              </Sidebar.Item>
            </Sidebar.ItemGroup>
          </Sidebar.Items>
        </Sidebar>
      </div>
      {/* nav bar 고정.. */}
      {/* <div
        class="min-h-screen h-full px-3 py-4 overflow-y-auto border-r"
        style={{ opacity: 0 }}
      >
        <Sidebar aria-label="Sidebar with logo branding example">
          <Sidebar.Logo img="/carbon.svg" imgAlt="Flowbite logo">
            <Link to="/">ECO2</Link>
          </Sidebar.Logo>
          <Sidebar.Items>
            <Sidebar.ItemGroup>
              <Sidebar.Item icon={HiInbox}>
                <Link to="/">Home</Link>
              </Sidebar.Item>
              <Sidebar.Item icon={HiViewBoards}>
                <Link to="/server">Server</Link>
              </Sidebar.Item>
              <Sidebar.Item icon={HiChartPie}>
                <Link to="/info">Info</Link>
              </Sidebar.Item>
            </Sidebar.ItemGroup>
          </Sidebar.Items>
        </Sidebar>
      </div> */}
    </div>
  );
}

function Home() {
  return (
    <div>
      <Code />
      <Stat />
    </div>
  );
}

function App() {
  return (
    <div class="flex">
      <CarbonProvider>
        <HWProvider>
          <Router>
            <div class="flex-col">
              <div style={{ width: "300px" }}></div>
              <Nav />
            </div>
            <div class="flex-col flex-grow">
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/server" element={<Server />} />
                <Route path="/info" element={<Info />} />
              </Routes>
            </div>
          </Router>
        </HWProvider>
      </CarbonProvider>
    </div>
  );
}

export default App;
