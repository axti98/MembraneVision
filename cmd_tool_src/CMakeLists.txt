cmake_minimum_required(VERSION 3.22)
project(cmd_tool_src)

set(CMAKE_CXX_STANDARD 23)

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ../../)

add_executable(tool main.cpp)

find_package(OpenMP)
if(OpenMP_CXX_FOUND)
    target_link_libraries(tool PUBLIC OpenMP::OpenMP_CXX)
endif()

set(Boost_INCLUDE_DIR C:\\boost_1_79_0)

find_package(Boost)
include_directories(${Boost_INCLUDE_DIR})


target_link_libraries(tool LINK_PUBLIC ${Boost_LIBRARIES})