import { Navigate } from "react-router-dom";
import useRecoilUser from "hooks/useRecoilUser";

type PublicRouteProps = {
  children: JSX.Element;
  restricted: boolean;
};

export default function PublicRoute({
  children,
  restricted,
}: PublicRouteProps) {
  const { user } = useRecoilUser();

  if (user && restricted) {
    return <Navigate to="/" replace />;
  }
  return children;
}
