import Header from "components/Header";
import useAuth from "hooks/useAuth";
import { Outlet } from "react-router-dom";
import { useEffect } from "react";
import useRecoilUser from "hooks/useRecoilUser";
import Cookies from "js-cookie";

export default function App() {
  const { reissue } = useAuth();
  const { user } = useRecoilUser();
  const loggedIn = Cookies.get("logged_in");

  useEffect(() => {
    if (loggedIn && !user) {
      reissue
        .mutateAsync() //
        .catch((error: Error) => alert(error.message));
    }
  }, []);

  return (
    <>
      <Header />
      <Outlet />
    </>
  );
}
