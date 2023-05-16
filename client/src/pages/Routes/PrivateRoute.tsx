import { Navigate } from "react-router-dom";
import useRecoilUser from "hooks/useRecoilUser";
import Cookies from "js-cookie";

export default function PrivateRoute({ children }: { children: JSX.Element }) {
  const { user } = useRecoilUser();
  const loggedIn = Cookies.get("logged_in");

  if (!user && !loggedIn) {
    return <Navigate to="/login" replace />;
  }
  return children;
}
