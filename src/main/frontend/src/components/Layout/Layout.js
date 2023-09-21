import Footer from "./Footer";
import Header from "./Header";
import Sidebar from "./Sidebar";
import styles from "../../styles/css/ContentDetail.module.css";
import { Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <div className={styles.wrapper}>
      <Header></Header>
      <div className={styles.contentWrapper}>
        <Outlet></Outlet>
      </div>
      <Footer></Footer>
      <div className={styles.divsidebar}>
        <Sidebar></Sidebar>
      </div>
    </div>
  );
};

export default Layout;
