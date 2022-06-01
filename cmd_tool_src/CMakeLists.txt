cmake_minimum_required(VERSION 3.22)
project(cmd_tool_src)

set(CMAKE_CXX_STANDARD 23)

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ../../)
add_executable(tool main.cpp)
