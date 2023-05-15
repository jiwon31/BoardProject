import { useMutation } from "@tanstack/react-query";
import AuthApi from "api/auth-api";
import UserApi from "api/user-api";
import { LoginRequest, SignUpRequest } from "types/auth";
import useRecoilUser from "./useRecoilUser";
import Cookies from "js-cookie";

export default function useAuth(
  authApi = new AuthApi(),
  userApi = new UserApi()
) {
  const { setUser } = useRecoilUser();
  const getUserInfo = () =>
    userApi
      .getUserInfo() //
      .then((data) => setUser(data));

  const signUp = useMutation<void, Error, SignUpRequest>((data) =>
    authApi.signUp(data)
  );

  const login = useMutation<void, Error, LoginRequest>(
    (data) => authApi.login(data),
    {
      onSuccess: () => {
        getUserInfo();
        Cookies.set("logged_in", "yes", { path: "/", expires: 7 });
      },
    }
  );

  const reissue = useMutation<void, Error>(() => authApi.reissue(), {
    onSuccess: getUserInfo,
  });

  const logout = useMutation<void, Error>(authApi.logout, {
    onSuccess: () => {
      setUser(null);
      Cookies.remove("logged_in");
    },
  });

  return { signUp, login, reissue, logout };
}
