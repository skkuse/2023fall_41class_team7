import "./index.css";

function Info() {
  return (
    <div class="flex justify-center min-h-screen pt-10">
      <div class="flex flex-col w-full max-w-7xl">
        <div class="text-3xl font-extrabold ml-10 mb-10 text-gray-800 text-center">Service Information</div>
        <div class="container p-4 m-4 bg-white shadow-lg rounded">
          <h2 class="text-2xl mb-2 text-black-600 text-center">problem : CO2</h2>
          <p class="text-gray-700 mb-2 text-center">인간의 여러가지 활동으로 인해 탄소 배출이 지속적으로 증가함에 따라,</p>
          <p class="text-gray-700 mb-2 text-center">이산화탄소 농도가 높아져 지구 온난화가 가속되고 있습니다.</p>
          <p class="text-gray-700 mb-2 text-center">이는 전 세계적으로 가뭄과 같은 극단적인 기후 현상을 더 자주 발생시키며,</p>
          <p class="text-gray-700 mb-2 text-center">평균 기온을 상승시켜 지구 생태계에 심각한 영향을 끼치고 있습니다.</p>
          <p class="text-gray-700 mb-2 text-center">탄소 배출로 인해 지구 생태계에 미치는 심각성을 인지할 필요가 있습니다.</p>
        </div>

        <div className="flex justify-evenly">
        <div className="flex-grow">
          <div className="container p-4 m-4 bg-white shadow-lg rounded">
            <h2 className="text-2xl mb-2 text-black-600 text-center">그린 소프트웨어(Green SW)란?</h2>
            <p className="text-gray-700 mb-2 text-center">환경 친화적인 방법으로 개발되고 운영되는 소프트웨어를 말합니다.</p>
            <p className="text-gray-700 mb-2 text-center">에너지 효율성, 재사용성, 재활용성, 그리고 지속 가능성 등을 중요한 요소로 간주합니다.</p>
            <p className="text-gray-700 mb-2 text-center">전력 소비를 최소화하고, 장기적으로 환경에 미치는 영향을 줄이는 것을 목표로 합니다.</p>
            <p className="text-gray-700 mb-2 text-center">코드 최적화, 에너지 효율적인 알고리즘 사용 등의 방법을 통해 달성할 수 있습니다.</p>
            <p className="text-gray-700 mb-2 text-center">지구를 위해 불필요한 SW 전력 소비를 줄여보세요!</p>
          </div>
        </div>
        <div className="flex-grow ml-4">
          <div className="container p-4 m-4 bg-white shadow-lg rounded">
            <h2 className="text-2xl mb-2 text-black-600 text-center">무엇을 제공하나요?</h2>
            <p className="text-gray-700 mb-2 text-center">사용자의 자바 소스코드의 탄소 배출량을 계산하여 결과값을 제공합니다.</p>
            <p className="text-gray-700 mb-2 text-center">결과값을 통해 사용자는 코드 최적화 작업을 진행해 볼 수 있으며,</p>
            <p className="text-gray-700 mb-2 text-center">그 과정에서 탄소 배출량을 지속적으로 확인해 볼 수 있습니다.</p>
            <p className="text-gray-700 mb-2 text-center">계산 결과값을 수치뿐만 아니라, 각종 교통수단의 탄소 배출량으로</p>
            <p className="text-gray-700 mb-2 text-center">값을 치환하여 보다 와닿게 시각적으로 표시해 줍니다.</p>
          </div>
        </div>
      </div>

        <div class="container p-4 m-4 bg-white shadow-lg rounded">
          <h2 class="text-2xl mb-2 text-black-600 text-center">Formula</h2>
          <p class="text-gray-700 mb-2 text-center">계산 공식</p>
        </div>

        <div class="container p-4 m-4 bg-white shadow-lg rounded">
          <h2 class="text-2xl mb-2 text-black-600 text-center">입력 데이터 형식</h2>
          <p class="text-gray-700 mb-2 text-center">입력 데이터 형식</p>
        </div>

        <div class="container p-4 m-4 bg-white shadow-lg rounded text-center">
          <h2 class="text-2xl mb-2 text-black-600 text-center">More Information</h2>
          <a href="https://github.com/skkuse/2023fall_41class_team7" target="_blank" rel="noopener noreferrer" class="text-blue-500 underline">
            GitHub
          </a>
        </div>

      </div>
    </div>
  );
}

export default Info;
