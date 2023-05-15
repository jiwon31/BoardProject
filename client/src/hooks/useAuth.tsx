import { useMutation } from "@tanstack/react-query";
import AuthApi from "api/auth-api";
import UserApi from "api/user-api";
import { LoginRequest, SignUpRequest } from "types/auth";
import useRecoilUser from "./useRecoilUser";

export default function useAuth(
  authApi = new AuthApi(),
  userApi = new UserApi()
) {
  const { setUser } = useRecoilUser();

  const signUp = useMutation<void, Error, SignUpRequest>((data) =>
    authApi.signUp(data)
  );

  const login = useMutation<void, Error, LoginRequest>(
    (data) => authApi.login(data),
    {
      onSuccess: () =>
        userApi
          .getUserInfo() //
          .then((data) => setUser(data)),
    }
  );

  return { signUp, login };
}
