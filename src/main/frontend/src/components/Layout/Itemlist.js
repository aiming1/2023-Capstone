import React, { useState, useEffect } from "react";
import axios from 'axios';
import styles from "../../styles/css/ItemList.module.css";
import {ListItem} from "../ListItem";

const ItemList = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const fetchData = async () => {
    try {
      // 요청이 시작 할 때에는 error 와 users 를 초기화하고
      setError(null);
      setData(null);
      // loading 상태를 true 로 바꿉니다.
      setLoading(true);
      const response = await axios.get(
          '/api'
      );
      setData(response.data); // 데이터는 response.data 안에 들어있습니다.
    } catch (e) {
      setError(e);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);


  if (loading) return <div>로딩중..</div>;
  if (error) return <div>에러가 발생했습니다</div>;
  if (!data) return null;
  return (
      <div className={styles.itemlistcontent}>
        {data.map((item) => (
            <ListItem
                className={styles.listItem}
                key={item.name}
                id={item.id}
                store={item.market}
                price={item.price}
                title={item.name}
                src={item.image}
                heartCnt={item.heartCnt}
            />
        ))}
      </div>
  );
}

export default ItemList;
