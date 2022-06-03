cmake_minimum_required(VERSION 3.22)
project(cmd_tool_src)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ../../)

find_package(OpenMP)

# Where to find CMake modules and OpenCV
set(OpenCV_DIR "C:\\opencv\\build_mingw\\install")
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/")

find_package(OpenCV REQUIRED)
include_directories(${OpenCV_INCLUDE_DIRS})

# add libs you need
set(OpenCV_LIBS opencv_core opencv_imgproc opencv_highgui opencv_imgcodecs)

add_executable(tool main.cpp Const.h SegmentationHandler.cpp SegmentationHandler.h)

# linking
target_link_libraries(tool ${OpenCV_LIBS})
if(OpenMP_CXX_FOUND)
    target_link_libraries(tool OpenMP::OpenMP_CXX)
endif()