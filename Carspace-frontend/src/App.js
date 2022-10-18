import './App.css';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';
import { Route, Routes, BrowserRouter as Router } from "react-router-dom";
import MainPage from './pages/MainPage/MainPage.js';
import NavBar from './components/NavBar/NavBar';
import Footer from './components/Footer/Footer';
import Login from './pages/Login/Login'
import RegisterPage from './pages/RegisterPage/RegisterPage';
import CreateAuction from './pages/CreateAuction/CreateAuction';
import EditAuction from './pages/EditAuction/EditAuction';
import AuctionDetails from './pages/AuctionDetails/AuctionDetails';
import ProfilePage from './pages/ProfilePage/ProfilePage';
import { toast } from 'react-toastify';
import { useEffect, useContext, useState } from 'react';
import { useCookies } from 'react-cookie';
import { checkLoginKey } from './service/userService';
import { UserContext } from './UserProvider';
import Protected from './components/Protected/Protected';
function App() {
  const [cookies, setCookie, removeCookie] = useCookies();
  const {loggedUser, setLoggedUser} = useContext(UserContext);
  const [isBusy, setIsBusy] = useState(true);

  useEffect(()=>{
    console.log("getting user");
    async function getUser() {
      try {
        const res = await checkLoginKey();
        setLoggedUser(res.obj);
        setIsBusy(false)
      } catch (err) {
        toast.error(err);
        removeCookie('token', {path:'/'});
        setLoggedUser(null);
      }
    }

    if(cookies["token"] !== undefined){
      getUser();
    }
  }, [])
  return (
    <div className="App">
      {!isBusy && <Router>
          <NavBar />
          <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/login" element={<Login />} />

          <Route path="/profile" element={
            <Protected loggedUser={loggedUser}>
              <ProfilePage />
            </Protected>
          } />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/auction/create" element={
            <Protected loggedUser={loggedUser}>
              <CreateAuction />
            </Protected>
          } />

          <Route path="/auction/edit/:auctionId" element={
            <Protected loggedUser={loggedUser}>
              <EditAuction />
            </Protected>
          } />
          <Route path="/auction/details/:auctionId" element={<AuctionDetails />} />
          </Routes>
        <Footer/>
      </Router>
      }
      
      <ToastContainer hideProgressBar={true}/>
    </div>
  );
}

export default App;
