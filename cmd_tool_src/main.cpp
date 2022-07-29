#include <iostream>
#include <fstream>
#include "Const.h"
#include <stdexcept>
#include <string>
#include "SegmentationHandler.h"
#include <numbers>
#include "alglib/solvers.h"
#include "alglib//stdafx.h"

int exit_code = CORRECT_EXIT_CODE;

bool is_neighbour(int** neighbour_list, int vertex, int neighbour)
{
    for(int i = 0; i < MAX_NEIGHBOURS; i++)
    {
        if(neighbour_list[vertex][i] == neighbour)
            return true;
    }

    return false;
}

int main(int argc, char *argv[]) {

    const int image_count = argc - ARR_POS_IMG_BEGIN;

    if (argc < MIN_REQU_ARGS) {
        std::cerr << "Not enough arguments passed to convert" << std::endl;
        exit_code = NO_ARG_EXIT_CODE;
        throw std::invalid_argument("Invalid amount of arguments passed");
    }

    int expansion_degree = static_cast<int>(std::strtol(argv[ARR_POS_EXP_DEG], nullptr, DEFAULT_RADIX));
    int voxel_size = static_cast<int>(std::strtol(argv[ARR_POS_DIS], nullptr, DEFAULT_RADIX));

    auto *image_paths = new std::string[image_count];
    std::cout << "Image count: " << image_count << std::endl;

    for (int i = ARR_POS_IMG_BEGIN; i < argc; i++) {
        image_paths[i - ARR_POS_IMG_BEGIN] = argv[i];
    }

    std::cout << "Expansion Degree: " << expansion_degree << "\n" << "Voxel size: " << voxel_size << std::endl;
    for (int i = 0; i < image_count; i++) {
        std::cout << "Img " << i << ": " << image_paths[i] << std::endl;
    }

    SegmentationHandler sh(image_paths, image_count);
    auto *segmentet_imgs = sh.segmentation();
    auto neighbour_list = new int **[image_count];

    int *vertices_count = new int[image_count];
    for(int k = 0; k < image_count; k++)
    {
        vertices_count[k] = 0;
    }

    for(int k = 0; k < image_count; k++) {
        for (int i = 0; i < segmentet_imgs[k].rows; i++) {
            for (int j = 0; j < segmentet_imgs[k].cols; j++) {
                if (static_cast<int>(segmentet_imgs[k].at<uchar>(i, j)) == 0) continue;
                else vertices_count[k]++;
            }
        }
        vertices_count[k] *= 4;
    }

    for (int i = 0; i < image_count; i++)
        neighbour_list[i] = sh.neighbouring(segmentet_imgs[i]);

    auto direct_neighbours = new int**[image_count];
    auto direct_neighbours_count = new int*[image_count];

    for(int img_iterator = 0; img_iterator < image_count; img_iterator++) {
        const int MAX_VERTEX = vertices_count[img_iterator];

        direct_neighbours[img_iterator] = new int *[MAX_VERTEX];
        direct_neighbours_count[img_iterator] = new int[MAX_VERTEX];

        for (int vert_iterator = 0; vert_iterator < MAX_VERTEX; vert_iterator++) {

            int current_vertex_count = 0;
            std::vector<int> temp_direct_neighbours;

            std::printf("Vertex: %2d, Neighbours:", vert_iterator);

            for(int i = 0; i < MAX_NEIGHBOURS; i++)
            {
                int ne = neighbour_list[img_iterator][vert_iterator][i];
                if(ne == IS_NOT_VOXEL) {
                    std::printf("  ");
                    continue;
                }
                std::printf(" %2d", ne);
            }

            for (int neigh_iterator = 0; neigh_iterator < MAX_NEIGHBOURS; neigh_iterator += DIRECT_NEIGHBOUR_STEP) {
                int ne = neighbour_list[img_iterator][vert_iterator][neigh_iterator];

                if(ne == IS_NOT_VOXEL)
                    continue;
                else {
                    current_vertex_count++;
                    temp_direct_neighbours.push_back(ne);
                }
            }

            std::printf(" Direct neighbours:");

            std::reverse(temp_direct_neighbours.begin(), temp_direct_neighbours.end());

            direct_neighbours_count[img_iterator][vert_iterator] = current_vertex_count;
            direct_neighbours[img_iterator][vert_iterator] = new int[current_vertex_count];
            for(int neigh_iterator = 0; neigh_iterator < current_vertex_count; neigh_iterator++)
            {
                direct_neighbours[img_iterator][vert_iterator][neigh_iterator] = temp_direct_neighbours.at(neigh_iterator);
                std::printf(" %2d", temp_direct_neighbours.at(neigh_iterator));
            }

            std::printf("\n");
        }

        int inner_verts_count = MAX_VERTEX - DIRECT_NEIGHBOUR_STEP;
        if(inner_verts_count <= 0) continue;

        auto **latitude_matrix = new double *[inner_verts_count];
        auto *latitude_vector = new double [inner_verts_count];

        for(int vert_iterator = 0; vert_iterator < inner_verts_count; vert_iterator++)
        {
            latitude_matrix[vert_iterator] = new double[inner_verts_count];
            latitude_vector[vert_iterator] = 0.0;

            for(int neigh_iterator = 0; neigh_iterator < inner_verts_count; neigh_iterator++)
            {
                latitude_matrix[vert_iterator][neigh_iterator] = 0.0;
            }
        }

        for(int vert_iterator1 = 0; vert_iterator1 < inner_verts_count; vert_iterator1++)
        {
            int current_direct_neighbour_count = direct_neighbours_count[img_iterator][vert_iterator1 + 1];
            latitude_matrix[vert_iterator1][vert_iterator1] = static_cast<double>(current_direct_neighbour_count);

            // Poles are set to j = 0 (north) and j = inner_verts_count+1 (south)
            // Border conditions supply tNorth = 0 and tSouth = pi

            for(int d = 0; d < current_direct_neighbour_count; d++)
            {
                int cur_neighbour = direct_neighbours[img_iterator][vert_iterator1][d];

                if(cur_neighbour == 0 || cur_neighbour == MAX_VERTEX)
                    continue;
                else
                    std::cout << cur_neighbour << std::endl;
                    //latitude_matrix[vert_iterator1][cur_neighbour] = -1.0;
            }
        }

        for(int i = 0; i < inner_verts_count; i++) {
            for (int j = 0; j < inner_verts_count; j++) {
                //std::printf("%2.0f ", latitude_matrix[i][j]);
            }
            //std::cout << std::endl;
        }

        // TODO: Check if latitude vector is correct (probably not)
        for(int vert_iterator = 0; vert_iterator < direct_neighbours_count[img_iterator][inner_verts_count + 1]; vert_iterator++){
            latitude_vector[direct_neighbours[img_iterator][inner_verts_count + 1][vert_iterator]] = std::numbers::pi;
        }

        // Following the example on https://www.alglib.net/translator/man/manual.cpp.html#example_linlsqr_d_1
        alglib::sparsematrix sparsed_latitude_matrix;
        alglib::sparsecreate(inner_verts_count, inner_verts_count, sparsed_latitude_matrix);

        for(int vert_iterator = 0; vert_iterator < inner_verts_count; vert_iterator++)
        {
            for(int neigh_iterator = 0;  neigh_iterator < inner_verts_count; neigh_iterator++)
            {
                alglib::sparseset(sparsed_latitude_matrix,
                                  vert_iterator,
                                  neigh_iterator,
                                  latitude_matrix[vert_iterator][neigh_iterator]
                                  );
            }
        }

        alglib::sparseconverttocrs(sparsed_latitude_matrix);

        std::string tmp = "[";
        for(int vert_iterator = 0; vert_iterator < inner_verts_count; vert_iterator++)
        {
            tmp += std::to_string(latitude_vector[vert_iterator]) + ",";
        }

        tmp = tmp.substr(0, tmp.length()-1) + "]";

        alglib::real_1d_array b = tmp.c_str();

        alglib::linlsqrstate s;
        alglib::linlsqrreport rep;
        alglib::real_1d_array x;
        alglib::linlsqrcreate(inner_verts_count, inner_verts_count, s);
        alglib::linlsqrsolvesparse(s, sparsed_latitude_matrix, b);
        alglib::linlsqrresults(s, x, rep);

        //std::printf("%s\n", x.tostring(2).c_str());

        // Nonlinear optimization of the obtained parameters

        // Calculation of spherical harmonics descriptors

        // Remove rotation dependency

        // Be happy cause this fucked up shit is ready

        for(int vert_iterator = 0; vert_iterator < inner_verts_count; vert_iterator++)
        {
            delete [] latitude_matrix[vert_iterator];
        }

        delete [] latitude_matrix;
        delete [] latitude_vector;
    }

    for(int img_iterator = 0; img_iterator < image_count; img_iterator++)
    {
        for(int i = 0; i < NEIGHBOUR_MULTIPLIER(segmentet_imgs[img_iterator].rows); i++)
        {
            delete [] neighbour_list[img_iterator][i];
        }

        delete [] neighbour_list[img_iterator];

        for(int i = 0; i < vertices_count[img_iterator]; i++)
        {
            delete [] direct_neighbours[img_iterator][i];
        }

        delete [] direct_neighbours[img_iterator];
    }

    delete [] direct_neighbours;

    delete [] neighbour_list;
    delete [] image_paths;
    delete [] vertices_count;

    system("pause");

    return exit_code;
}