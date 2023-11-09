import "./index.css";
import { Sidebar } from "flowbite-react";
import {
  HiArrowSmRight,
  HiChartPie,
  HiInbox,
  HiShoppingBag,
  HiTable,
  HiUser,
  HiViewBoards,
} from "react-icons/hi";
import { BrowserRouter as Router, Link, Route, Routes } from "react-router-dom";
import Code from "./Code";
import Stat from "./Stat";

function Nav() {
  return (
    <div>
      <div class="min-h-screen h-full px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800 border-r">
        <Sidebar aria-label="Sidebar with logo branding example">
          <Sidebar.Logo img="/carbon.svg" imgAlt="Flowbite logo">
            <Link to="/">Carbon Calculator</Link>
          </Sidebar.Logo>
          <Sidebar.Items>
            <Sidebar.ItemGroup>
              <Sidebar.Item icon={HiChartPie}>
                <Link to="/">Home</Link>
              </Sidebar.Item>
              <Sidebar.Item icon={HiViewBoards}>
                <Link to="/">Server</Link>
              </Sidebar.Item>
              <Sidebar.Item icon={HiInbox}>
                <Link to="/">Statistics</Link>
              </Sidebar.Item>
              {/* <Sidebar.Item icon={HiUser}>
                <Link to="/">Users</Link>
              </Sidebar.Item>
              <Sidebar.Item icon={HiShoppingBag}>
                <Link to="/">Products</Link>
              </Sidebar.Item>
              <Sidebar.Item icon={HiArrowSmRight}>
                <Link to="/">Sign In</Link>
              </Sidebar.Item>
              <Sidebar.Item icon={HiTable}>
                <Link to="/">Sign Up</Link>
              </Sidebar.Item> */}
            </Sidebar.ItemGroup>
          </Sidebar.Items>
        </Sidebar>
      </div>
    </div>
  );
}
function App() {
  return (
    <div class="flex">
      {/* <Code /> */}
      <Router>
        <div class="flex-col">
          <Nav />
        </div>
        <div class="flex-col">
          <Routes>
            <Route path="/" element={<Code />} />
            <Route path="statistics" element={<Stat />} />
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;
