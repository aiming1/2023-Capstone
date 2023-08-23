import { useEffect } from "react";
import styles from "../../styles/css/Sidebar.module.css";
import { useNavigate } from "react-router-dom";

function Sidebar(props) {
  //props에 detail item 넣어줘야함. item img 경로 정해지면 변경.
  const navigate = useNavigate();

  if (!localStorage.getItem("watched")){
    localStorage.setItem("watched", JSON.stringify([]));}
  // 처음에 최근 본 상품이 아무것도 없으면 watched 만듬.

  const lsts = localStorage.getItem("watched");
  const lstss = JSON.parse(lsts).reverse();

  return (
    <div className={styles.div}>
      <div className={styles.box}>
        <div className={styles.title}>최근 본 상품</div>
        {lstss.map((it) => {
          return (
            <img
              className={styles.item}
              alt=""
              src= {it[2]}
              style={{
              borderRadius: 5,
              margin: 5,}}
              onClick={() => {
                navigate(`/product/${it[0]}`,{
                state: {
                    id: it[0],
                    market: it[1],
                    image: it[2],
                    price: it[3]
                }});
                window.location.reload()
              }}
            />
          );
        })}
      </div>
    </div>
  );
}

export default Sidebar;
