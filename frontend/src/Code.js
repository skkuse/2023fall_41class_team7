import React from "react";
import "./index.css";
import "./Code.css";
import AceEditor from "react-ace";
import Stat from "./Stat";
import { useContext, useState, useEffect } from "react";
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/ext-language_tools";
import { CarbonContext, CarbonProvider } from "./Carbon";
import { HWContext, HWProvider } from "./Hardware";
import axios from 'axios';
import Modal from 'react-modal';


const customStyles = {
  overlay: {
    backgroundColor: " rgba(0, 0, 0, 0.4)",
    width: "100%",
    height: "100vh",
    zIndex: "10",
    position: "fixed",
    top: "0",
    left: "0",
    opacity: "85%",
  },
  content: {
    display: 'flex',      // Center content vertically
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    width: "360px",
    height: "180px",
    zIndex: "150",
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    borderRadius: "10px",
    boxShadow: "2px 2px 2px rgba(0, 0, 0, 0.25)",
    backgroundColor: "white",
    overflow: "auto",
    fontFamily: 'Arial, sans-serif', // Choose your preferred font
    fontSize: '1.5rem',  // Adjust the font size as needed
    fontWeight: 'bold',
    gap: "30px",
  },
};


function Innercomponent() {
  const [height, setHeight] = useState(500);
  const { HWValue, setHWValue } = useContext(HWContext);
  const { carbonValue, setCarbonValue } = useContext(CarbonContext);
  const [errorMessage, setErrorMessage] = useState(null);
  const [popState, setPopState] = useState(false);
  const [javaCodeValue, setJavaCodeValue] = useState(`public class Main {
  public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}`);


  const CarbonList = async (sender) => {
    console.log(JSON.stringify(sender));
    try {
      const response = await axios.post("http://ec2-3-35-3-126.ap-northeast-2.compute.amazonaws.com:8080/green", sender)
      const { data } = response.data;
      console.log(data);
      setCarbonValue(response.data);

    } catch (error) {
      console.log(error.response.data.errorDetail);
      setErrorMessage(JSON.stringify(error.response.data.errorDetail.reasonMessage).replace(/"/g, ''));
      setPopState(true);
    }

    // .then((res => {
    //   setCarbonValue(res.data);
    // }))
    // .catch((e) => {
    //   const reasonMessage = e.response.data.errorDetail.reasonMessage;
    //   console.log(e);
    //   alert(reasonMessage);

    // })
  };

  function onChange(newValue) {

    setJavaCodeValue(newValue);

  }



  useEffect(() => {

    setHWValue({
      ...HWValue,
      javaCode: javaCodeValue,
    });
  }, [javaCodeValue]);

  useEffect(() => {
    console.log(carbonValue);
  }, [carbonValue]);

  const onClick = () => {
    setHeight(1000);
  };

  const BtnClick = () => {
    const isKorean = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/.test(javaCodeValue);

    if (isKorean) {
      // alert("Korean is not allowed\nPlease remove Korean.(Including Annotation)")
      setErrorMessage("한글을 제거해주세요( 주석포함 )");
      setPopState(true);
    }
    else {
      const sender = HWValue;
      console.log(sender);
      CarbonList(sender);
    }

  }

  const onClear = () => {
    setJavaCodeValue(`public class Main {
      public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}`);
    setHeight(500);
  }

  const onClose = () => {
    setPopState(false);
  }

  return (
    <div className="w-full">
      <div class=" m-10">
        <h1 class="mb-4 text-3xl font-extrabold text-gray-900 dark:text-white md:text-5xl lg:text-6xl">
          <span class="text-transparent bg-clip-text bg-gradient-to-r to-emerald-600 from-sky-400">
            Input 
          </span>{" "}
          Java Code
        </h1>
      </div>

      <div className={'code_area start '} onClick={onClick}>
        <Modal
          isOpen={popState} onRequestClose={() => setPopState(false)}
          style={customStyles}
        >
          <p>{errorMessage}</p>
          <button className="rounded bg-blue-500 px-4 py-2 font-bold text-white hover:bg-blue-600" onClick={onClose}>Close</button>

        </Modal>
        <AceEditor
          height={height.toString() + "px"}
          width='100%'
          placeholder="Placeholder Text"
          mode="java"
          theme="monokai"
          name="blah2"
          onChange={onChange}
          // onLoad={this.onLoad}
          fontSize={20}
          showPrintMargin={true}
          showGutter={true}
          highlightActiveLine={true}
          value={javaCodeValue}

          setOptions={{
            enableBasicAutocompletion: false,
            enableLiveAutocompletion: false,
            enableSnippets: false,
            showLineNumbers: true,
            tabSize: 4,
          }}

        />
      </div>
      <div style={{ display: 'flex', justifyContent: "right", gap: 10, padding: 10 }}>
        {/* <button className="rounded bg-blue-500 px-4 py-2 font-bold text-white hover:bg-blue-600" >Upload</button> */}
        <button className="rounded bg-red-500 px-4 py-2 font-bold text-white hover:bg-red-600" onClick={onClear}>Clear</button>
        <button className="rounded bg-green-500 px-4 py-2 font-bold text-white hover:bg-green-600" onClick={BtnClick}>Submit</button>
      </div>

    </div>
  );
}

function Code() {
  return (
    <>
      <Innercomponent />
    </>
  );
}

export default Code;
