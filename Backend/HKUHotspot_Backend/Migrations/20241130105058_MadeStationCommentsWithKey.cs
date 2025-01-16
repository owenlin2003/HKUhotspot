using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace HKUHotspot_Backend.Migrations
{
    /// <inheritdoc />
    public partial class MadeStationCommentsWithKey : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "Id",
                table: "HKUStationComments",
                type: "int",
                nullable: false,
                defaultValue: 0)
                .Annotation("SqlServer:Identity", "1, 1");

            migrationBuilder.AddPrimaryKey(
                name: "PK_HKUStationComments",
                table: "HKUStationComments",
                column: "Id");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_HKUStationComments",
                table: "HKUStationComments");

            migrationBuilder.DropColumn(
                name: "Id",
                table: "HKUStationComments");
        }
    }
}
