import React, { useState, useEffect } from "react";
import { useLocation } from "react-router";
import { useNavigate } from "react-router-dom";
import HeartButton from "./HeartButton";
import ImageSlide from "./ImageSlide";
import Modal from "./ImageModal";
import styles from "../styles/css/ContentDetail.module.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

import axios from "axios";

const ContentDetail = (props) => {
  const product = useLocation();
  const productId = product.state.id;
  const productMarket = product.state.market[0];
  const productImage = product.state.image;
  const productPrice = product.state.price;
  const navigate = useNavigate();

  const [data, setData] = useState(null);
  const [data2, setData2] = useState(null);
  const [heart, setHeart] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedImage, setSelectedImage] = useState("");
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .all([
        axios.get(`/api/product/${productId}/${productMarket}`, {}),
        axios.get("/api/list", {}),
      ])
      .then(
        axios.spread((res, res2) => {
          const data = res.data;
          const data2 = res2.data;
          setData(data);
          setData2(data2);

          let isHeart = data2.some((x) => {
            return x?.id === data?.id;
          });
          setHeart(isHeart);

          if (!localStorage.getItem("token")) {
            //console.log("로그아웃됨"); 로그아웃 안됨-임시추가
            setData2(null);
            setHeart(false);
          }
          //console.log("heart", isHeart, data2);

          //data?.updatedate 값이 -로 시작할때 함수 호출
          if (data?.updatedate && data.updatedate.startsWith('-')) {
            const updatedateMinutes = handleNegativeUpdatedate(data?.updatedate);
            setData((prevData) => ({
              ...prevData,
              updatedate: updatedateMinutes,
            }));
          }
        })
      )
      .catch((e) => {
        setError(e);
        //console.log("에러...");
      });
  }, []);

  //
  /*const fetchData = async () => {
    setData(null);
    const response = await axios.get(
      `/api/product/${productId}/${productMarket}`
    );
    setData(response.data);
  };
  useEffect(() => {
    fetchData();
  }, []); */

  function handleNegativeUpdatedate(inputSeconds) {
    const std = 32405;
    // 입력값을 숫자로 변환
    const seconds = parseFloat(inputSeconds);

    if (isNaN(seconds)) {
      // 유효하지 않은 입력값 처리
      console.error('유효하지 않은 입력값입니다.');
      return null;
    }

    let unit = '초';
    let resultValue = std + seconds;

    if (resultValue >= 60) {  // 60 이상일 경우 분 단위로 변환
      resultValue /= 60;
      unit = '분';

      if (resultValue >= 60) {  // 60 이상일 경우 시간 단위로 변환
        resultValue /= 60;
        unit = '시간';

        if (resultValue >= 24) {  // 24 이상일 경우 일 단위로 변환
          resultValue /= 24;
          unit = '일';
        }
      }
    }

    // 결과값을 내림하여 정수로 변환
    resultValue = Math.floor(resultValue);

    return `${resultValue}${unit} 전`;
  }

  const renderLogo = () => {
    if (data?.market === "CARROT") {
      //당근마켓
      return (
        <div className={styles.wrapicon}>
          <img
            className={styles.icon6}
            alt=""
            src="/img/당근마켓 아이콘 1.png"
          />{" "}
          <span className={styles.div20}>당근마켓</span>
        </div>
      );
    } else if (data?.market === "BUNJANG") {
      //번개장터
      return (
        <div className={styles.wrapicon}>
          <img
            className={styles.icon6}
            alt=""
            src="/img/번개장터 아이콘 1.png"
          />
          <span className={styles.div20}>번개장터</span>
        </div>
      );
    } else if (data?.market === "JOONGGONARA") {
      //중고나라
      return (
        <div className={styles.wrapicon}>
          <img
            className={styles.icon6}
            alt=""
            src="/img/중고나라 아이콘.png"
          />
          <span className={styles.div20}>중고나라</span>
        </div>
      );
    }
    return null;
  };

  //찜하기
  const addHeart = async () => {
    if (!localStorage.getItem("token")) {
      alert("로그인 후 이용해주세요!");
      navigate("/login");}
    else {
      const productData = {
        id: productId,
        name: data?.name,
        image: data?.image,
        price: data?.price,
        market: productMarket,
        seller: data?.seller,
        updatedate: data?.updatedate,
        views: data?.views,
        hearts: data?.hearts,
        details: data?.details,
        category: data?.category,
        producturl: data?.producturl,
        region: data?.region,
      };

      axios
        .get(`/api/product/${productId}/${productMarket}/heart/add`, productData)
        .then(function (response) {
          console.log("추가 성공", response);
          setHeart(!heart);
          //alert("찜목록에 추가되었습니다.");
          // response
        })
        .catch(function (error) {
          // 오류발생시 실행
          console.log("실패", error);
        })
        .then(function () {
          // 항상 실행
          console.log("데이터 요청 완료");
        });
    }
  };


  //찜 해제
  const deleteHeart = async () => {
    axios
      .get(`/api/product/${productId}/${productMarket}/heart/delete`)
      .then(function (response) {
        console.log("삭제 성공", response);
        setHeart(!heart);
        alert("찜목록에서 제거합니다.");
      })
      .catch(function (error) {
        // 오류발생시 실행
        console.log("실패", error);
      })
      .then(function () {
        // 항상 실행
        console.log("데이터 요청 완료");
      });
  };

  const lsts = localStorage.getItem("watched");
  useEffect(() => {
    let lst = JSON.parse(lsts);
    let dtail = [productId, productMarket, productImage, productPrice];
    const isSame = JSON.stringify(lst).includes(JSON.stringify(dtail));
    if (!isSame) {
      if (lst.length > 4) {
        lst.shift();
      }
      lst.push(dtail);
      lst = new Set(lst);
      lst = Array.from(lst);
      localStorage.setItem("watched", JSON.stringify(lst));
    }
  }, []);
  // 최근 본  상품.
  // detail 들어가면 product id를 watched에 추가

  // 이미지 클릭 시 모달 열기
  const openModal = (image) => {
    setSelectedImage(image);
    setIsModalOpen(true);
  };

  // 모달 닫기
  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className={styles.productPage}>
      {/*<img className={styles.icon} alt="" src={data?.image[0]} />*/}
      <div className={styles.productTop}>
        <div className={styles.productImageWrapper}>
          <div className={styles.group}>
            <ImageSlide images={data?.image} onImageClick={openModal} />
          </div>
        </div>
        {isModalOpen && <Modal image={selectedImage} onClose={closeModal} />}

        <div className={styles.productSummaryWrapper}>
          <div className={styles.parent}>
            <div className={styles.productDetails}>
              <div className={styles.category}>
                <div className={styles.homebtn}>
                  {/* <i class="fas fa-home" style={{ marginRight: '5px' }}></i>*/}
                  홈 {">"}
                </div>
                <div
                  className={styles.categoryname}
                  style={{ marginLeft: "5px" }}
                >
                  {/*<i className="fas fa-angle-right" style={{ margin: 'auto 10px' }}></i>*/}
                  {data?.category == null ? "기타 카테고리" : data?.category}
                </div>
                {/*                {`홈 > `}
                {data?.category == null ? "카테고리" : data?.category}*/}
              </div>
              <div className={styles.title}>{data?.name}</div>
              <div className={styles.name}>{data?.seller}</div>
              <div className={styles.location}>
                <i
                  class="fas fa-map-marker-alt"
                  style={{ fontSize: "15px", margin: "0 5px" }}
                ></i>
                희망 거래지역 : {data?.region == null ? "전국" : data?.region}
              </div>
              <div className={styles.state}>
                <div className={styles.date}>
                  <i
                    class="far fa-clock"
                    style={{ fontSize: "15px", margin: "5px" }}
                  ></i>
                  {data?.updatedate == null ? "0분 전" : data?.updatedate}
                </div>
                <div className={styles.views}>
                  <i
                    class="fas fa-search"
                    style={{ fontSize: "15px", margin: "5px" }}
                  ></i>
                  조회 {data?.views == null ? "0" : data?.views}
                </div>
                <div className={styles.heart}>
                  <i class="fas fa-heart" style={{ fontSize: '15px', margin: '5px' }}></i>
                  찜 {data?.hearts == null ? "0" : data?.hearts}
                </div>
              </div>
              <div className={styles.price}>{productPrice}원</div>
            </div>

            <div className={styles.productButtons}>
              <div
                className={styles.btn_golink}
                onClick={() => {
                  window.open(data?.producturl);
                }}
              >
                <div className={styles.child}>보러 가기</div>
                {/*<div className={styles.div7}></div>*/}
              </div>

              <span className={styles.shortline} />
              {heart ? (
                <HeartButton heart={heart} onClick={deleteHeart}></HeartButton>
              ) : (
                <HeartButton heart={heart} onClick={addHeart}></HeartButton>
              )}
            </div>
          </div>
        </div>
      </div>

      <div className={styles.container}>
        <div className={styles.div21}>{renderLogo()}</div>
      </div>

      <div className={styles.line}></div>

      <div className={styles.productInfo}>
        <div className={styles.infoHeadLine}>상품 설명</div>
        <div className={styles.description}>
          {data?.details.split("\n").map((line, index) => (
            <React.Fragment key={index}>
              {line}
              <br />
            </React.Fragment>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ContentDetail;