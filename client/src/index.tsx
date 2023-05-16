import ReactDOM from "react-dom/client";
import App from "App";
import "./index.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { RecoilRoot } from "recoil";
import Home from "pages/Home";
import Login from "pages/Login";
import Signup from "pages/Signup";
import Mypage from "pages/Mypage";
import WriteBoard from "pages/WriteBoard";
import BoardDetail from "pages/BoardDetail";
import NotFound from "pages/NotFound";
import PrivateRoute from "pages/Routes/PrivateRoute";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
      retry: false,
    },
    mutations: {
      retry: false,
    },
  },
});

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      { index: true, path: "/", element: <Home /> },
      { path: "/login", element: <Login /> },
      { path: "/signup", element: <Signup /> },
      {
        path: "/mypage",
        element: (
          <PrivateRoute>
            <Mypage />
          </PrivateRoute>
        ),
      },
      {
        path: "/boards/new",
        element: (
          <PrivateRoute>
            <WriteBoard />
          </PrivateRoute>
        ),
      },
      {
        path: "/boards/:id",
        element: (
          <PrivateRoute>
            <BoardDetail />
          </PrivateRoute>
        ),
      },
      { path: "*", element: <NotFound /> },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <RecoilRoot>
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
      <ReactQueryDevtools initialIsOpen={true} />
    </QueryClientProvider>
  </RecoilRoot>
);
