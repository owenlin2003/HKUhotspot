using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace HKUHotspot_Backend.Migrations
{
    /// <inheritdoc />
    public partial class PositionToFloatAndHKUStationDB : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<float>(
                name: "PositionY",
                table: "HKUUsers",
                type: "real",
                nullable: false,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<float>(
                name: "PositionX",
                table: "HKUUsers",
                type: "real",
                nullable: false,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.CreateTable(
                name: "HKUStation",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Description = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    CurrentOccupancy = table.Column<int>(type: "int", nullable: false),
                    StartPositionX = table.Column<float>(type: "real", nullable: false),
                    StartPositionY = table.Column<float>(type: "real", nullable: false),
                    EndPositionX = table.Column<float>(type: "real", nullable: false),
                    EndPositionY = table.Column<float>(type: "real", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_HKUStation", x => x.ID);
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "HKUStation");

            migrationBuilder.AlterColumn<string>(
                name: "PositionY",
                table: "HKUUsers",
                type: "nvarchar(max)",
                nullable: false,
                oldClrType: typeof(float),
                oldType: "real");

            migrationBuilder.AlterColumn<string>(
                name: "PositionX",
                table: "HKUUsers",
                type: "nvarchar(max)",
                nullable: false,
                oldClrType: typeof(float),
                oldType: "real");
        }
    }
}
